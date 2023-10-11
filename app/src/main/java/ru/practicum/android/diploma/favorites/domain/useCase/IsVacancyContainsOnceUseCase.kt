package ru.practicum.android.diploma.favorites.domain.useCase

interface IsVacancyContainsOnceUseCase {
    suspend fun execute(id: Int): Boolean
}