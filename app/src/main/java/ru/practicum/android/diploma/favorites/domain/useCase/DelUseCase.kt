package ru.practicum.android.diploma.favorites.domain.useCase

interface DelUseCase {
    suspend fun execute(id: Int): Boolean
}
