package ru.practicum.android.diploma.vacancy.data.repositoryImpl

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl: VacancyRepository {
    override suspend fun findVacancy(vacancyId: Int): Vacancy {
        TODO("Not yet implemented")
    }

    override suspend fun getVacancyById(vacancyId: Int): Vacancy {
        TODO("Not yet implemented")
    }
}