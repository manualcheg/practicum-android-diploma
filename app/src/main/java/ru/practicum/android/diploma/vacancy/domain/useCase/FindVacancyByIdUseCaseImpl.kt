package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class FindVacancyByIdUseCaseImpl(private val repository: VacancyRepository) : FindVacancyByIdUseCase {
    override suspend fun findVacancyById(vacancyId: Int): Vacancy {
        return repository.findVacancyById(vacancyId)
    }
}