package ru.practicum.android.diploma.search.data.dataSourceImpl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.model.SearchRequest
import ru.practicum.android.diploma.search.data.network.HeadHunterApiService
import ru.practicum.android.diploma.search.data.network.Response
import ru.practicum.android.diploma.search.domain.repository.NetworkConnectionProvider

class VacancyRemoteDataSourceImpl(
    private val headHunterApiService: HeadHunterApiService,
    private val networkConnectionProvider: NetworkConnectionProvider
) : VacancyRemoteDataSource {

    override suspend fun doRequest(dto: Any): Response {
        if (!networkConnectionProvider.isConnected()) {
            return Response().apply { resultCode = NO_CONNECTION }
        }
        if (dto !is SearchRequest) {
            return Response().apply { resultCode = RESPONSE_BAD_REQUEST }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = headHunterApiService.search(dto.options)
                response.apply { resultCode = RESPONSE_SUCCESS }
            } catch (e: Throwable) {
                Response().apply { resultCode = RESPONSE_ERROR }
            }
        }
    }

    companion object {
        const val NO_CONNECTION = -1
        const val RESPONSE_SUCCESS = 200
        const val RESPONSE_BAD_REQUEST = 400
        const val RESPONSE_ERROR = 500
    }
}