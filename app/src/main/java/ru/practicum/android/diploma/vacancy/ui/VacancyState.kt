package ru.practicum.android.diploma.vacancy.ui

import ru.practicum.android.diploma.common.ui.model.VacancyUi

sealed interface VacancyState {
    class Load() : VacancyState

    class Error() : VacancyState

    class Content(val vacancy: VacancyUi) : VacancyState
}
