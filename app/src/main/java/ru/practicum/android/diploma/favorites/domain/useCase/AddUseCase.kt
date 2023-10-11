package ru.practicum.android.diploma.favorites.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface AddUseCase {
    suspend fun execute(vacancy: Vacancy): Boolean
}