package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class LoadVacancyFromDatabaseUseCaseImpl(private val repository: VacancyRepository) :
    LoadVacancyFromDatabaseUseCase {
    override suspend fun getVacancyById(vacancyId: Int): Vacancy {
        return repository.getVacancyById(vacancyId)
    }
}