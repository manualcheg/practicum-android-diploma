package ru.practicum.android.diploma.favorites.domain.useCase

interface AddOrDelVacancyUseCase {
    suspend fun execute(id: Int): Boolean
}