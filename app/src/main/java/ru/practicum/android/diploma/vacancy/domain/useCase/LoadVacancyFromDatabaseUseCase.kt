package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface LoadVacancyFromDatabaseUseCase {
    suspend fun getVacancyFromDataBaseById(vacancyId: Int): Vacancy
}