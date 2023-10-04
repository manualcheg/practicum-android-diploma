package ru.practicum.android.diploma.search.ui.model

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy


sealed interface SearchState {
    object Loading : SearchState

    sealed class Success(open val vacancies: List<Vacancy>) : SearchState {
        data class SearchContent(
            override val vacancies: List<Vacancy>
        ) : Success(vacancies)

        object Empty : Success(emptyList())
    }

    data class Error(
        val errorStatus: ErrorStatusUi
    ) : SearchState

}