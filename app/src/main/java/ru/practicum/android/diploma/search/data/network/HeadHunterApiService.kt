package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.model.SearchResponse

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun search(
        @QueryMap options: Map<String, String>
    ): SearchResponse
}