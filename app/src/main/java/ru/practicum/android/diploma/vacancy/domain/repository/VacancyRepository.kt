package ru.practicum.android.diploma.vacancy.domain.repository

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface VacancyRepository {
    suspend fun findVacancy(vacancyId: Int): Vacancy
    suspend fun getVacancyFromDataBaseById(vacancyId: Int): Vacancy
}