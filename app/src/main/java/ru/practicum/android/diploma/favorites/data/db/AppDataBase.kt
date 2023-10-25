package ru.practicum.android.diploma.favorites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [FavoritesVacancyEntity::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun FavoritesVacanciesDao(): FavoritesVacanciesDao
}