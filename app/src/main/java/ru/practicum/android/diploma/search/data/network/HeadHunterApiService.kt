package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.model.SearchResponse
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchResponse

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun search(
        @QueryMap options: Map<String, String>
    ): SearchResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun searchVacancyById(@Path("vacancy_id") vacancyId: Int): VacancySearchResponse
}