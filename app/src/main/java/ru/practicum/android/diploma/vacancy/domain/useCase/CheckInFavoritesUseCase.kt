package ru.practicum.android.diploma.vacancy.domain.useCase

import kotlinx.coroutines.flow.Flow

interface CheckInFavoritesUseCase {
    suspend fun execute(id: Int): Flow<Boolean>
}