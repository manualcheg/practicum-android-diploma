package ru.practicum.android.diploma.similar_vacancy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.DEFAULT_MAX_PAGES
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.DEFAULT_PAGE
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.DEFAULT_PER_PAGE
import ru.practicum.android.diploma.common.util.constants.VacanciesViewModelConst.PAGE_LIMIT
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchError
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.search.ui.model.SingleLiveEvent
import ru.practicum.android.diploma.search.ui.viewModel.SearchViewModel
import ru.practicum.android.diploma.similar_vacancy.domain.useCase.SearchSimilarVacanciesByIdUseCase

class SimilarVacancyViewModel(
    val vacancyId: Int,
    private val searchSimilarVacanciesByIdUseCase: SearchSimilarVacanciesByIdUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter

) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchState>()
    private val toastErrorStateLiveData = SingleLiveEvent<SearchError>()

    private var foundVacancies = 0
    private var currentPages = 0
    private var nextPage = 0
    private var maxPages = DEFAULT_MAX_PAGES
    private var perPage = SearchViewModel.DEFAULT_PER_PAGE

    private var isNextPageLoading = false

    private val vacanciesList = mutableListOf<VacancyUi>()

    init {
        searchNewRequest(vacancyId)
    }

    fun observeState(): LiveData<SearchState> = stateLiveData
    fun observeErrorToastState(): LiveData<SearchError> = toastErrorStateLiveData

    fun onLastItemReached() {
        searchSameRequest(vacancyId)
    }

    private fun setState(state: SearchState) {
        stateLiveData.value = state
    }

    private fun setToastErrorState(state: SearchError) {
        toastErrorStateLiveData.value = state
    }

    private fun searchNewRequest(vacancyId: Int) {

        setState(SearchState.Loading.LoadingSearch)

        nextPage = DEFAULT_PAGE
        viewModelScope.launch {
            getVacancies(vacancyId, nextPage, perPage, isNewSearch = true)
        }
        nextPage++
    }

    private fun searchSameRequest(vacancyId: Int) {
        if (currentPages == maxPages && currentPages == PAGE_LIMIT || isNextPageLoading) {
            return
        }
        isNextPageLoading = true
        setState(SearchState.Loading.LoadingPages)

        if (PAGE_LIMIT - currentPages <= DEFAULT_PER_PAGE) {
            perPage = PAGE_LIMIT - currentPages
        }

        viewModelScope.launch {
            val resultDeferred = async {
                getVacancies(vacancyId, nextPage, perPage, isNewSearch = false)
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
                        } else {
                            setToastErrorState(SearchError.NO_CONNECTION)
                        }
                    }

                    ErrorStatusDomain.ERROR_OCCURRED -> {
                        if (isNewSearch) {
                            setState(SearchState.Error(ErrorStatusUi.ERROR_OCCURRED))
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
        vacancyId: Int, nextPage: Int, perPage: Int, isNewSearch: Boolean
    ) {
        searchSimilarVacanciesByIdUseCase.execute(vacancyId, nextPage, perPage = perPage).collect {
            processResult(it.first, it.second, isNewSearch)
        }
    }
}