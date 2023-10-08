package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface FindVacancyByIdUseCase {
    suspend fun findVacancyById(vacancyId: Int): Pair<Vacancy?, ErrorStatusDomain?>
}