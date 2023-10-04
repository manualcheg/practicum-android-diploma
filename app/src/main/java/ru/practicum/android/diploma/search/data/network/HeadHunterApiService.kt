package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.model.SearchResponse

interface HeadHunterApiService {
    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Jobseeker (judjingm@gmail.com)")
    @GET("/vacancies")
    suspend fun search(@Query("text") text: String): SearchResponse
}