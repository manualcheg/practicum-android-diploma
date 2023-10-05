package ru.practicum.android.diploma.favorites.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesStorage
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesDBRepository

class FavoritesDBRepositoryImpl(
    private val favoritesStorage: FavoritesStorage,
) : FavoritesDBRepository {
    override suspend fun addToFavorites(vacancy: Vacancy) {
        favoritesStorage.addToFavorites(vacancy)
    }

    override suspend fun deleteFromFavorites(id: Int) {
        favoritesStorage.deleteFromFavorites(id)
    }

    override suspend fun getFavorites(): Flow<List<Vacancy>> = flow {
        val listOfVacancies = favoritesStorage.getFavorites()
        emit(listOfVacancies)
    }

    override suspend fun getVacancy(id: Int): Vacancy {
        return favoritesStorage.getVacancy(id)
    }
}