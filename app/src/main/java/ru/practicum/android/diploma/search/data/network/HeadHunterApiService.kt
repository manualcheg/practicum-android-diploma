package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.model.SearchResponse

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun search(@Query("text") text: String): SearchResponse
}