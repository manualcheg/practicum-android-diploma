package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface FindVacancyUseCase {
    suspend fun findVacancy(vacancyId: Int): Vacancy
}