package ru.practicum.android.diploma.favorites.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesVacanciesDao {
    @Insert(entity = FavoritesVacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(vacancy: FavoritesVacancyEntity)

    //может упростить? @Delete вместо @Query
    @Query("DELETE FROM favorites_vacancies_table WHERE id = :id")
    suspend fun deleteFromFavorites(id: Int)

    @Query("SELECT * FROM favorites_vacancies_table ORDER BY createdAt DESC")
//    suspend fun getFavorites(): Flow<List<FavoritesVacancyEntity>>
    suspend fun getFavorites(): List<FavoritesVacancyEntity>

    //для просмотра вакансии из БД. Можно и для проверки присуствия в Избранном.
    @Query("SELECT * FROM favorites_vacancies_table WHERE id = :id")
    suspend fun getVacancy(id: Int): FavoritesVacancyEntity
}

/*
private fun mapList(favouritesList: List<FavoritesVacancyEntity>): List<Vacancy> {
    val vacancyDbConverter: VacancyDbConverter
    return favouritesList.map { favoritesVacancyEntity -> vacancyDbConverter.map(favoritesVacancyEntity) }
}
*/
