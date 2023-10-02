package ru.practicum.android.diploma.vacancy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesVacanciesDao {
    @Insert(entity = FavoritesVacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(track: FavoritesVacancyEntity)

    @Query("DELETE FROM favorites_vacancies_table WHERE id = :id")
    suspend fun deleteFromFavorites(id: Int)

    @Query("SELECT * FROM favorites_vacancies_table ORDER BY createdAt DESC")
    suspend fun getFavorites(): List<FavoritesVacancyEntity>
}