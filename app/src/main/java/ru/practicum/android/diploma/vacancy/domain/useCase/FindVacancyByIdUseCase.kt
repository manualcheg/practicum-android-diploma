package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.vacancy.domain.model.VacancyOrError

interface FindVacancyByIdUseCase {
    suspend fun findVacancyById(vacancyId: Int): VacancyOrError
}