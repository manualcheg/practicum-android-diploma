package ru.practicum.android.diploma.favorites.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

class AddUseCaseImpl(private val favoritesRepository: FavoritesRepository):AddUseCase {
    override suspend fun execute(vacancy: Vacancy) {
        favoritesRepository.addToFavorites(vacancy)
    }
}