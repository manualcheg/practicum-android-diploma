package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesDBRepository

class AddToFavoritesUseCaseImpl(private val favoritesDBRepository: FavoritesDBRepository) :
    AddToFavoritesUseCase {
    override suspend fun execute(vacancy: Vacancy) {
        favoritesDBRepository.addToFavorites(vacancy)
    }
}

class DeleteFromFavoritesUseCaseImpl(private val favoritesDBRepository: FavoritesDBRepository) :
    DeleteFromFavoritesUseCase {
    override suspend fun execute(id: Int) {
        favoritesDBRepository.deleteFromFavorites(id)
    }
}

class GetFavoritesUseCaseImpl(private val favoritesDBRepository: FavoritesDBRepository) :
    GetFavoritesUseCase {
    override suspend fun execute(): Flow<List<Vacancy>> {
        return favoritesDBRepository.getFavorites()
    }
}

class GetVacancyUseCaseImpl(private val favoritesDBRepository: FavoritesDBRepository) :
    GetVacancyUseCase {
    override suspend fun execute(id: Int): Vacancy {
        return favoritesDBRepository.getVacancy(id)
    }
}