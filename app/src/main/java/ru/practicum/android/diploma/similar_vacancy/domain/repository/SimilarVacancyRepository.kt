package ru.practicum.android.diploma.similar_vacancy.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource

interface SimilarVacancyRepository {
    fun searchSimilarVacanciesById(id: Int, page: Int, perPage: Int): Flow<Resource<Vacancies>>
}