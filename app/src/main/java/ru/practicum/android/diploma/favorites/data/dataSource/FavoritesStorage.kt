package ru.practicum.android.diploma.favorites.data.dataSource

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

interface FavoritesStorage {
    suspend fun addToFavorites(vacancy: Vacancy)

    suspend fun deleteFromFavorites(id: Int)

    suspend fun getFavorites(): List<Vacancy>

    suspend fun getVacancy(id: Int): Vacancy
}