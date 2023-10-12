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
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchError
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.search.ui.model.SingleLiveEvent

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter
) : ViewModel() {

    private var latestSearchText: String? = null

    private val stateLiveData = MutableLiveData<SearchState>()
    private val toastErrorStateLiveData = SingleLiveEvent<SearchError>()

    private var foundVacancies = DEFAULT_FOUND_VACANCIES
    private var currentPages = DEFAULT_PAGE
    private var nextPage = DEFAULT_PAGE
    private var maxPages = DEFAULT_MAX_PAGES
    private var perPage = DEFAULT_PER_PAGE

    private var isNextPageLoading = false
    private val vacanciesList = mutableListOf<VacancyUi>()

    private var job: Job? = null

    private val tracksSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchNewRequest(it)
        }

    fun observeState(): LiveData<SearchState> = stateLiveData
    fun observeErrorToastState(): LiveData<SearchError> = toastErrorStateLiveData

    fun searchDebounced(changedText: String) {
        if (changedText == latestSearchText) {
            return
        }
        latestSearchText = changedText
        nextPage = 0
        tracksSearchDebounce(changedText)
    }

    fun onLastItemReached() {
        latestSearchText?.let { searchSameRequest(it) }
    }

    fun clearSearchInput() {
        setState(SearchState.Success.Empty)
        tracksSearchDebounce(DEFAULT_TEXT)
        job?.cancel()
    }

    fun restoreState() {
        setState(SearchState.Success.SearchContent(vacanciesList, foundVacancies))
    }

    private fun setState(state: SearchState) {
        stateLiveData.value = state
    }

    private fun setToastErrorState(state: SearchError) {
        toastErrorStateLiveData.value = state
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
        if (currentPages == maxPages && currentPages == PAGE_LIMIT || isNextPageLoading) {
            return
        }
        isNextPageLoading = true
        setState(SearchState.Loading.LoadingPages)

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


    private fun processResult(
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
                        if (isNewSearch) {
                            setState(SearchState.Error(ErrorStatusUi.NO_CONNECTION))
                            latestSearchText = DEFAULT_TEXT
                        } else {
                            setToastErrorState(SearchError.NO_CONNECTION)
                        }
                    }

                    ErrorStatusDomain.ERROR_OCCURRED -> {
                        if (isNewSearch) {
                            setState(SearchState.Error(ErrorStatusUi.ERROR_OCCURRED))
                            latestSearchText = DEFAULT_TEXT
                        } else {
                            setToastErrorState(SearchError.ERROR_OCCURRED)
                        }
                    }
                }
            }

            vacanciesList.isEmpty() -> setState(SearchState.Error(ErrorStatusUi.NOTHING_FOUND))

            else -> {
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