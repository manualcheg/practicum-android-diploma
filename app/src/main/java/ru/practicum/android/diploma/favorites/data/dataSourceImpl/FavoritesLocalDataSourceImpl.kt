package ru.practicum.android.diploma.favorites.data.dataSourceImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesLocalDataSource
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter

class FavoritesLocalDataSourceImpl(
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
) : FavoritesLocalDataSource {
    override suspend fun addToFavorites(vacancy: Vacancy) {
        appDataBase.FavoritesVacanciesDao().addToFavorites(vacancyDbConverter.mapVacancyToFavoriteVacancyEntity(vacancy))
    }

    override suspend fun deleteFromFavorites(vacancy: Vacancy) {
        appDataBase.FavoritesVacanciesDao().deleteFromFavorites(vacancyDbConverter.mapVacancyToFavoriteVacancyEntity(vacancy))
    }

    override fun getFavorites(): Flow<List<Vacancy>> {
        return mapList(appDataBase.FavoritesVacanciesDao().getFavorites())
    }

    override suspend fun getVacancy(id: Int): Vacancy {
        return vacancyDbConverter.mapFavoriteVacancyEntityToVacancy(appDataBase.FavoritesVacanciesDao().getVacancy(id))
    }

    override fun isVacancyContainsFlow(id: Int): Flow<Boolean> {
        return appDataBase.FavoritesVacanciesDao().isVacancyContainsFlow(id)
    }

    override suspend fun isVacancyContains(id: Int): Boolean {
        return appDataBase.FavoritesVacanciesDao().isVacancyContains(id)
    }

    private fun mapList(favouritesList: Flow<List<FavoritesVacancyEntity>>): Flow<List<Vacancy>> {
        return favouritesList.map { listFavoritesVacancyEntity ->
            listFavoritesVacancyEntity.map { favoritesVacancyEntity ->
                vacancyDbConverter.mapFavoriteVacancyEntityToVacancy(
                    favoritesVacancyEntity
                )
            }
        }
    }
}