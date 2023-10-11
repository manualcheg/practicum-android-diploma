package ru.practicum.android.diploma.favorites.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface DelUseCase {
    suspend fun execute(vacancy: Vacancy)
}
