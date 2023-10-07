package ru.practicum.android.diploma.search.ui.model

import ru.practicum.android.diploma.common.ui.model.VacancyUi


sealed interface SearchState {
    object Loading : SearchState

    sealed class Success(open val vacancies: List<VacancyUi>) : SearchState {
        data class SearchContent(
            override val vacancies: List<VacancyUi>
        ) : Success(vacancies)

        object Empty : Success(emptyList())
    }

    data class Error(
        val errorStatus: ErrorStatusUi
    ) : SearchState

}