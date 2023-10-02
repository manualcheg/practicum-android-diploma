package ru.practicum.android.diploma.search.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Vacancy

interface SearchUseCase {
    fun search(expression: String): Flow<Pair<List<Vacancy>?, Int?>>
}