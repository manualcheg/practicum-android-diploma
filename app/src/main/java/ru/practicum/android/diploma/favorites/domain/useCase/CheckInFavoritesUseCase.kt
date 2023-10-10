package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow

interface CheckInFavoritesUseCase {
    suspend fun execute(id: Int): Flow<Boolean>
}