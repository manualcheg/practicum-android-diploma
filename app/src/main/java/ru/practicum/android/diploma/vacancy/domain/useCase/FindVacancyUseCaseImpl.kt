package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class FindVacancyUseCaseImpl(private val repository: VacancyRepository) : FindVacancyUseCase {
    override suspend fun findVacancy(vacancyId: Int): Vacancy {
        return repository.findVacancy(vacancyId)
    }
}