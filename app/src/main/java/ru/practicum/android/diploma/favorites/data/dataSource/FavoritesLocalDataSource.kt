package ru.practicum.android.diploma.favorites.data.dataSource

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface FavoritesLocalDataSource {
    suspend fun addToFavorites(vacancy: Vacancy)

    suspend fun deleteFromFavorites(vacancy: Vacancy)

    fun getFavorites(): Flow<List<Vacancy>>

    suspend fun getVacancy(id: Int): Vacancy

    fun isVacancyContainsFlow(id: Int): Flow<Boolean>

    suspend fun isVacancyContains(id: Int): Boolean
}