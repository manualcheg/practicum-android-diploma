package ru.practicum.android.diploma.favorites.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesDBRepository

class FavoritesDBRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
) : FavoritesDBRepository {
    override suspend fun addToFavorites(vacancy: Vacancy) {
        appDataBase.FavoritesVacanciesDao().addToFavorites(vacancyDbConverter.map(vacancy))
    }

    override suspend fun deleteFromFavorites(id: Int) {
        appDataBase.FavoritesVacanciesDao().deleteFromFavorites(id)
    }

    override suspend fun getFavorites(): Flow<List<Vacancy>> = flow {
        val listOfVacancies = appDataBase.FavoritesVacanciesDao().getFavorites()
        emit(mapList(listOfVacancies))
    }

    override suspend fun getVacancy(id: Int): Vacancy {
        return vacancyDbConverter.map(appDataBase.FavoritesVacanciesDao().getVacancy(id))
    }

    private fun mapList(favouritesList: List<FavoritesVacancyEntity>): List<Vacancy> {
        return favouritesList.map { track -> vacancyDbConverter.map(track)}
    }
}