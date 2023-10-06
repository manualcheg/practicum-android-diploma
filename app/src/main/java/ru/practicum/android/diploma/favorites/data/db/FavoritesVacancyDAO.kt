package ru.practicum.android.diploma.favorites.data.db

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

@Dao
interface FavoritesVacanciesDao {
    @Insert(entity = FavoritesVacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(vacancy: FavoritesVacancyEntity)

    //может упростить? @Delete вместо @Query(...)
    @Query("DELETE FROM favorites_vacancies_table WHERE id = :id")
    suspend fun deleteFromFavorites(id: Int)

    @Query("SELECT * FROM favorites_vacancies_table ORDER BY createdAt DESC")
    @Throws(SQLiteException::class)
    fun getFavorites(): Flow<List<FavoritesVacancyEntity>>

    //для просмотра вакансии из БД. Можно и для проверки присуствия в Избранном.
    @Query("SELECT * FROM favorites_vacancies_table WHERE id = :id")
    suspend fun getVacancy(id: Int): FavoritesVacancyEntity

    @Query("SELECT EXISTS(SELECT * FROM favorites_vacancies_table WHERE id = :id)")
    suspend fun isVacancyContains(id: Int): Boolean
}