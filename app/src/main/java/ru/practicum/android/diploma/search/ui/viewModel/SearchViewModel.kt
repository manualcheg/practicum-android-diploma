package ru.practicum.android.diploma.search.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchState

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter
) : ViewModel() {

    private var latestSearchText: String? = null

    private val stateLiveData = MutableLiveData<SearchState>()

    private var foundVacancies = 0
    private var currentPages = 0
    private var nextPage = 0
    private var maxPages = 1

    private var isNextPageLoading = false
    private val vacanciesList = mutableListOf<VacancyUi>()

    fun observeState(): LiveData<SearchState> = stateLiveData

    private val tracksSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchRequest(it)
        }

    fun searchDebounced(changedText: String) {
        if (changedText == latestSearchText) {
            return
        }
        latestSearchText = changedText
        vacanciesList.clear()
        nextPage = 0
        tracksSearchDebounce(changedText)
    }

    fun onLastItemReached() {
        latestSearchText?.let { searchRequest(it) }
    }

    private fun setState(state: SearchState) {
        stateLiveData.value = state
    }

    private fun searchRequest(inputSearchText: String) {
        if (currentPages == maxPages) {
            return
        }
        if (inputSearchText.isNotEmpty()) {
            if (vacanciesList.isEmpty()) {
                setState(SearchState.Loading.LoadingSearch)
            } else if (currentPages != maxPages) {
                setState(SearchState.Loading.LoadingPages)
            }
        }
        if (!isNextPageLoading) {
            isNextPageLoading = true
            viewModelScope.launch {
                val resultDeferred = async {
                    searchUseCase.search(inputSearchText, nextPage).collect {
                        processResult(it.first, it.second)
                        nextPage++
                    }
                }
                resultDeferred.await()
                isNextPageLoading = false
            }
        }
    }

    private fun processResult(vacancies: Vacancies?, errorStatus: ErrorStatusDomain?) {
        if (vacancies != null) {
            val foundVacancyUi =
                vacancies.vacancyList.map { vacancyDomainToVacancyUiConverter.map(it) }
            vacanciesList.addAll(foundVacancyUi)
            foundVacancies = vacancies.found
            currentPages = vacancies.page
            maxPages = vacancies.pages
        }
        when {
            errorStatus != null -> {
                when (errorStatus) {
                    ErrorStatusDomain.NO_CONNECTION -> {
                        setState(SearchState.Error(ErrorStatusUi.NO_CONNECTION))
                        latestSearchText = DEFAULT_TEXT
                    }

                    ErrorStatusDomain.ERROR_OCCURRED -> {
                        setState(SearchState.Error(ErrorStatusUi.ERROR_OCCURRED))
                        latestSearchText = DEFAULT_TEXT
                    }
                }
            }

            vacanciesList.isEmpty() -> setState(SearchState.Error(ErrorStatusUi.NOTHING_FOUND))

            else -> {
                setState(SearchState.Success.SearchContent(vacanciesList, foundVacancies))
            }
        }
    }

    fun clearSearchInput() {
        setState(SearchState.Success.Empty)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val DEFAULT_TEXT = ""
    }
}