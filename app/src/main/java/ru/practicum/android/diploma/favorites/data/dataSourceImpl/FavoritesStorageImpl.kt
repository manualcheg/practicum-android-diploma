package ru.practicum.android.diploma.favorites.data.dataSourceImpl

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesStorage
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter

class FavoritesStorageImpl(
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
) : FavoritesStorage {
    override suspend fun addToFavorites(vacancy: Vacancy) {
        appDataBase.FavoritesVacanciesDao().addToFavorites(vacancyDbConverter.map(vacancy))
    }

    override suspend fun deleteFromFavorites(id: Int) {
        appDataBase.FavoritesVacanciesDao().deleteFromFavorites(id)
    }

    //    override suspend fun getFavorites(): Flow<List<Vacancy>> {
    override suspend fun getFavorites(): List<Vacancy> {
        return mapList(appDataBase.FavoritesVacanciesDao().getFavorites())
//        return appDataBase.FavoritesVacanciesDao().getFavorites()
    }

    override suspend fun getVacancy(id: Int): Vacancy {
        return vacancyDbConverter.map(appDataBase.FavoritesVacanciesDao().getVacancy(id))
    }

    private fun mapList(favouritesList: List<FavoritesVacancyEntity>): List<Vacancy> {
        return favouritesList.map { favoritesVacancyEntity -> vacancyDbConverter.map(favoritesVacancyEntity) }
    }
}