package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface GetFavoritesUseCase {
    suspend fun execute(): Flow<List<Vacancy>>
}

interface AddOrDelVacancyUseCase {
    suspend fun execute(id: Int): Boolean
}

interface CheckInFavoritesUseCase {
    suspend fun execute(id: Int): Flow<Boolean>
}