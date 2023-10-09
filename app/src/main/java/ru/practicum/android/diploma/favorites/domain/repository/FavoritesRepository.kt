package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface FavoritesRepository {
    suspend fun addToFavorites(vacancy: Vacancy)

    suspend fun deleteFromFavorites(id: Int)

    fun getFavorites(): Flow<List<Vacancy>>

    suspend fun getVacancy(id: Int): Vacancy

    suspend fun isVacancyContains(id: Int): Boolean
}