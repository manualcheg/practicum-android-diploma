package ru.practicum.android.diploma.vacancy.data.repositoryImpl

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.NO_CONNECTION
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.RESPONSE_SUCCESS
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesLocalDataSource
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchRequest
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchResponse
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDtoConverter: VacancyDtoConverter,
) : VacancyRepository {
    override suspend fun findVacancyById(vacancyId: Int): Resource<Vacancy> {
        if (favoritesLocalDataSource.isVacancyContains(vacancyId)) {
            return Resource.Success(
                favoritesLocalDataSource.getVacancy(vacancyId)
            )
        } else {
            val response = vacancyRemoteDataSource.doRequest(VacancySearchRequest(vacancyId))
            when (response.resultCode) {

                NO_CONNECTION -> {
                    return Resource.Error(ErrorRemoteDataSource.NO_CONNECTION)
                }

                RESPONSE_SUCCESS -> {
                    val vacancy: Vacancy =
                        vacancyDtoConverter.mapVacancySearchResponseToVacancy((response as VacancySearchResponse))
                    return Resource.Success(vacancy)
                }

                else -> {
                    return Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED)
                }
            }
        }
    }

}