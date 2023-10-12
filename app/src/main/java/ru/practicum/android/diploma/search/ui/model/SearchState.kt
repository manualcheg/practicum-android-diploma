package ru.practicum.android.diploma.search.ui.model

import ru.practicum.android.diploma.common.ui.model.VacancyUi


sealed interface SearchState {
    sealed class Loading : SearchState {
        object LoadingSearch : Loading()
    }

    sealed class Success(open val vacancies: List<VacancyUi>, open val foundVacancy: Int = 0) :
        SearchState {
        data class SearchContent(
            override val vacancies: List<VacancyUi>, override val foundVacancy: Int
        ) : Success(vacancies, foundVacancy)

        object Empty : Success(emptyList())
    }

    data class Error(
        val errorStatus: ErrorStatusUi
    ) : SearchState

}