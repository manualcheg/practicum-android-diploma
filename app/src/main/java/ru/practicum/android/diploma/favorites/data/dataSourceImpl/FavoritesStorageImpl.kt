package ru.practicum.android.diploma.favorites.data.dataSourceImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getFavorites(): Flow<List<Vacancy>> {
        return mapList(appDataBase.FavoritesVacanciesDao().getFavorites())
    }

    override suspend fun getVacancy(id: Int): Vacancy {
        return vacancyDbConverter.map(appDataBase.FavoritesVacanciesDao().getVacancy(id))
    }

    private fun mapList(favouritesList: Flow<List<FavoritesVacancyEntity>>): Flow<List<Vacancy>> {
        return favouritesList.map { listFavoritesVacancyEntity ->
            listFavoritesVacancyEntity.map { favoritesVacancyEntity ->
                vacancyDbConverter.map(
                    favoritesVacancyEntity
                )
            }
        }
    }
}