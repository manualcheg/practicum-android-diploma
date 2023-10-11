package ru.practicum.android.diploma.favorites.domain.useCase

import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

class DelUseCaseImpl(private val favoritesRepository: FavoritesRepository):DelUseCase {
    override suspend fun execute(id: Int): Boolean {
        favoritesRepository.deleteFromFavorites(id)
        return false
    }
}