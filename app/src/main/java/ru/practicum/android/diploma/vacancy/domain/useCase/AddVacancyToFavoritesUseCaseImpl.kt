package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

class AddVacancyToFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    AddVacancyToFavoritesUseCase {
    override suspend fun execute(vacancy: Vacancy) {
        favoritesRepository.addToFavorites(vacancy)
    }
}