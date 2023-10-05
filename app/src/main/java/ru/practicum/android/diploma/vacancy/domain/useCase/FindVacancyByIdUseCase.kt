package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface FindVacancyByIdUseCase {
    suspend fun findVacancyById(vacancyId: Int): Vacancy
}