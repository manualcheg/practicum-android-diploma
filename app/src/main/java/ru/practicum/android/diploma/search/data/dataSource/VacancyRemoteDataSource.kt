package ru.practicum.android.diploma.search.data.dataSource

import ru.practicum.android.diploma.search.data.network.Response

interface VacancyRemoteDataSource {
    suspend fun doRequest(dto: Any): Response
}