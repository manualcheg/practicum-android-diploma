package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.models.Vacancy
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase

class SearchUseCaseImpl(private val repository: SearchRepository) : SearchUseCase {
    override fun search(expression: String): Flow<Pair<List<Vacancy>?, Int?>> {
        return repository.search(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.errorCode)
                }
            }
        }
    }

}