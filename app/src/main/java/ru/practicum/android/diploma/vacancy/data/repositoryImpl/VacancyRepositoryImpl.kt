package ru.practicum.android.diploma.vacancy.data.repositoryImpl

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.data.repositoryImpl.SearchRepositoryImpl
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchRequest
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchResponse
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter,
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDtoConverter: VacancyDtoConverter,
) : VacancyRepository {
    override suspend fun findVacancyById(vacancyId: Int): Resource<Vacancy> {
        // есть ли вакансия в базе данных?
        if (appDataBase.FavoritesVacanciesDao().isVacancyContains(vacancyId)) {
            return Resource.Success(
                vacancyDbConverter.map(
                    appDataBase.FavoritesVacanciesDao().getVacancy(vacancyId)
                )
            )
        } else {
            val response = vacancyRemoteDataSource.doRequest(VacancySearchRequest(vacancyId))
            when (response.resultCode) {

                SearchRepositoryImpl.NO_CONNECTION -> {
                    return Resource.Error(ErrorRemoteDataSource.NO_CONNECTION)
                }

                SearchRepositoryImpl.RESPONSE_SUCCESS -> {
                    val vacancy: Vacancy =
                        vacancyDtoConverter.map((response as VacancySearchResponse))
                    return Resource.Success(vacancy)
                }

                else -> {
                    return Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED)
                }
            }
        }
    }

}