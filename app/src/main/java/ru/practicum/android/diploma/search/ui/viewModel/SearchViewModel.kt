package ru.practicum.android.diploma.search.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.DEFAULT_MAX_PAGES
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.DEFAULT_PAGE
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.DEFAULT_PER_PAGE
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.PAGE_LIMIT
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.domain.useCase.IsFiltersExistsUseCase
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchError
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.search.ui.model.SingleLiveEvent

open class SearchViewModel(
    private val searchUseCase: SearchUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter,
    private val isFiltersExistsUseCase: IsFiltersExistsUseCase
) : ViewModel() {

    private var latestSearchText: String? = null

    private val stateLiveData = MutableLiveData<SearchState>()
    private val paginationLoadingState = SingleLiveEvent<Boolean>()
    private val toastErrorStateLiveData = SingleLiveEvent<SearchError>()
    private val filterButtonStateLiveData = MutableLiveData<Boolean>()

    private var foundVacancies = DEFAULT_FOUND_VACANCIES
    protected var currentPages = DEFAULT_PAGE
    protected var nextPage = DEFAULT_PAGE
    protected var maxPages = DEFAULT_MAX_PAGES
    protected var perPage = DEFAULT_PER_PAGE

    protected var isNextPageLoading = false
    private val vacanciesList = mutableListOf<VacancyUi>()

    private var job: Job? = null

    private val tracksSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchNewRequest(it)
        }

    fun observeState(): LiveData<SearchState> = stateLiveData
    fun observeErrorToastState(): LiveData<SearchError> = toastErrorStateLiveData
    fun observePaginationLoadingState(): LiveData<Boolean> = paginationLoadingState
    fun observeFilterButtonState(): LiveData<Boolean> = filterButtonStateLiveData


    fun searchDebounced(changedText: String) {
        if (changedText == latestSearchText) {
            return
        }
        latestSearchText = changedText
        nextPage = 0
        tracksSearchDebounce(changedText)
    }

    open fun onLastItemReached() {
        latestSearchText?.let { searchSameRequest(it) }
    }

    fun clearSearchInput() {
        setState(SearchState.Success.Empty)
        tracksSearchDebounce(DEFAULT_TEXT)
        job?.cancel()
    }

    fun getButtonState() {
        setFilterButtonState(isFiltersExistsUseCase.execute())
    }

    fun setState(state: SearchState) {
        stateLiveData.value = state
    }

    private fun setToastErrorState(state: SearchError) {
        toastErrorStateLiveData.value = state
    }

    protected fun setPaginationLoadingState(isLoading: Boolean) {
        paginationLoadingState.value = isLoading
    }

    private fun setFilterButtonState(isFiltersExist: Boolean) {
        filterButtonStateLiveData.value = isFiltersExist
    }

    private fun searchNewRequest(inputSearchText: String) {
        if (inputSearchText.isBlank()) {
            return
        }
        setState(SearchState.Loading.LoadingSearch)
        foundVacancies = DEFAULT_FOUND_VACANCIES

        nextPage = DEFAULT_PAGE
        job = viewModelScope.launch {
            getVacancies(inputSearchText, nextPage, perPage, isNewSearch = true)
        }
        nextPage++
    }


    private fun searchSameRequest(inputSearchText: String) {
        if (currentPages == maxPages || currentPages == PAGE_LIMIT || isNextPageLoading) {
            return
        }
        isNextPageLoading = true
        setPaginationLoadingState(true)

        if (PAGE_LIMIT - currentPages <= DEFAULT_PER_PAGE) {
            perPage = PAGE_LIMIT - currentPages
        }

        job = viewModelScope.launch {
            val resultDeferred = async {
                getVacancies(inputSearchText, nextPage, perPage, isNewSearch = false)
            }
            nextPage++
            resultDeferred.await()
            isNextPageLoading = false
        }
    }

    protected fun processResult(
        vacancies: Vacancies?, errorStatus: ErrorStatusDomain?, isNewSearch: Boolean
    ) {
        if (vacancies != null) {
            val foundVacancyUi =
                vacancies.vacancyList.map { vacancyDomainToVacancyUiConverter.map(it) }
            if (isNewSearch) {
                vacanciesList.clear()
            }
            vacanciesList.addAll(foundVacancyUi)
            foundVacancies = vacancies.found
            currentPages = vacancies.page
            maxPages = vacancies.pages
        }
        when {
            errorStatus != null -> {
                when (errorStatus) {
                    ErrorStatusDomain.NO_CONNECTION -> {
                        setPaginationLoadingState(false)
                        if (isNewSearch) {
                            setState(SearchState.Error(ErrorStatusUi.NO_CONNECTION))
                            latestSearchText = DEFAULT_TEXT
                        } else {
                            setToastErrorState(SearchError.NO_CONNECTION)
                        }
                    }

                    ErrorStatusDomain.ERROR_OCCURRED -> {
                        setPaginationLoadingState(false)
                        if (isNewSearch) {
                            setState(SearchState.Error(ErrorStatusUi.ERROR_OCCURRED))
                            latestSearchText = DEFAULT_TEXT
                        } else {
                            setToastErrorState(SearchError.ERROR_OCCURRED)
                        }
                    }
                }
            }

            vacanciesList.isEmpty() -> {
                setPaginationLoadingState(false)
                setState(SearchState.Error(ErrorStatusUi.NOTHING_FOUND))
            }

            else -> {
                setPaginationLoadingState(false)
                setState(SearchState.Success.SearchContent(vacanciesList, foundVacancies))
            }
        }
    }

    private suspend fun getVacancies(
        inputSearchText: String, nextPage: Int, perPage: Int, isNewSearch: Boolean
    ) {
        searchUseCase.search(inputSearchText, nextPage, perPage = perPage).collect {
            processResult(it.first, it.second, isNewSearch)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val DEFAULT_TEXT = ""
        const val DEFAULT_FOUND_VACANCIES = 0
    }
}