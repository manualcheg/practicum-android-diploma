package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface AddToFavoritesUseCase {
    suspend fun execute(vacancy: Vacancy)
}

interface DeleteFromFavoritesUseCase {
    suspend fun execute(id: Int)
}

interface GetFavoritesUseCase {
    suspend fun execute(): Flow<List<Vacancy>>
}

interface GetVacancyUseCase {
    suspend fun execute(id: Int): Vacancy
}