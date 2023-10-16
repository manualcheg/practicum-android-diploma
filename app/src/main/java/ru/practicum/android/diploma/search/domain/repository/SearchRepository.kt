package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource

interface SearchRepository {
    fun search(text: String, page: Int, perPage: Int): Flow<Resource<Vacancies>>
}