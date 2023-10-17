package ru.practicum.android.diploma.search.data.dataSourceImpl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.NO_CONNECTION
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.RESPONSE_SUCCESS
import ru.practicum.android.diploma.filter.data.model.AllAreasRequest
import ru.practicum.android.diploma.filter.data.model.AreasResponse
import ru.practicum.android.diploma.filter.data.model.CertainAreasRequest
import ru.practicum.android.diploma.filter.data.model.CountriesRequest
import ru.practicum.android.diploma.filter.data.model.CountriesResponse
import ru.practicum.android.diploma.filter.data.model.IndustriesRequest
import ru.practicum.android.diploma.filter.data.model.IndustriesResponse
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.model.SearchRequest
import ru.practicum.android.diploma.search.data.network.HeadHunterApiService
import ru.practicum.android.diploma.search.data.network.Response
import ru.practicum.android.diploma.search.domain.repository.NetworkConnectionProvider
import ru.practicum.android.diploma.similar_vacancy.data.model.SearchSimilarVacancyRequest
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchRequest

class VacancyRemoteDataSourceImpl(
    private val headHunterApiService: HeadHunterApiService,
    private val networkConnectionProvider: NetworkConnectionProvider
) : VacancyRemoteDataSource {

    override suspend fun doRequest(dto: Any): Response {
        if (!networkConnectionProvider.isConnected()) {
            return Response().apply { resultCode = NO_CONNECTION }
        }
        if (dto is SearchRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = headHunterApiService.search(dto.options)
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        }
        if (dto is VacancySearchRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = headHunterApiService.searchVacancyById(dto.vacancyId)
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        }

        if (dto is SearchSimilarVacancyRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = headHunterApiService.searchSimilarVacancies(
                        vacancyId = dto.vacancyId, options = dto.options
                    )
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        }

        if (dto is AllAreasRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val data = headHunterApiService.getAreas()
                    val response = AreasResponse(data)
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        }

        if (dto is CountriesRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val data = headHunterApiService.getCountries()
                    val response = CountriesResponse(data)
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        }

        if (dto is IndustriesRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val data = headHunterApiService.getIndustries()
                    val response = IndustriesResponse(data)
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        }

        if (dto is CertainAreasRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val data = headHunterApiService.getAreasById(dto.areaId)
                    val response = AreasResponse(listOf(data))
                    response.apply { resultCode = RESPONSE_SUCCESS }
                } catch (e: Throwable) {
                    Response().apply { resultCode = RESPONSE_ERROR }
                }
            }
        } else {
            return Response().apply { resultCode = RESPONSE_BAD_REQUEST }
        }
    }

    companion object {
        const val RESPONSE_BAD_REQUEST = 400
        const val RESPONSE_ERROR = 500
    }
}