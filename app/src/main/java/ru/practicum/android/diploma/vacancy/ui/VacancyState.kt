package ru.practicum.android.diploma.vacancy.ui

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

sealed class VacancyState() {
    class Load() : VacancyState()

    class Error() : VacancyState()

    class Content(val vacancy: Vacancy) : VacancyState()
}
