package ru.practicum.android.diploma.vacancy.domain.repository

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.util.Resource

interface VacancyRepository {
    suspend fun findVacancyById(vacancyId: Int): Resource<Vacancy>
}