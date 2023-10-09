package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.util.Resource

// интерфейс для связи слоя Domain со слоем Data
// интерфейс SearchRepository реализует SearchRepositoryImpl в data
interface SearchRepository {
    fun search(options: HashMap<String, String>): Flow<Resource<List<Vacancy>>>
}