package ru.practicum.android.diploma.search.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchState

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private var latestSearchText: String? = null

    private val stateLiveData = MutableLiveData<SearchState>()

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
        tracksSearchDebounce(changedText)
    }

    private fun setState(state: SearchState) {
        stateLiveData.value = state
    }

    private fun searchRequest(inputSearchText: String) {
        if (inputSearchText.isNotEmpty()) {
            setState(SearchState.Loading)

            viewModelScope.launch {
                searchUseCase.search(inputSearchText).collect {
                    processResult(it.first, it.second)
                }
            }
        }
    }

    private fun processResult(foundVacancies: List<Vacancy>?, errorStatus: ErrorStatusDomain?) {
        val vacancies = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancies.addAll(foundVacancies)
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

            vacancies.isEmpty() -> setState(SearchState.Error(ErrorStatusUi.NOTHING_FOUND))

            else -> {
                setState(SearchState.Success.SearchContent(vacancies))
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