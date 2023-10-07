package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

class AddToFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    AddToFavoritesUseCase {
    override suspend fun execute(vacancy: Vacancy) {
        favoritesRepository.addToFavorites(vacancy)
    }
}

class DeleteFromFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    DeleteFromFavoritesUseCase {
    override suspend fun execute(id: Int) {
        favoritesRepository.deleteFromFavorites(id)
    }
}

class GetFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    GetFavoritesUseCase {
    override suspend fun execute(): Flow<List<Vacancy>> {
        return favoritesRepository.getFavorites()
    }
}

class GetVacancyUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    GetVacancyUseCase {
    override suspend fun execute(id: Int): Vacancy {
        return favoritesRepository.getVacancy(id)
    }
}