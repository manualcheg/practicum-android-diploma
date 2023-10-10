package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.model.VacanciesSearchResponse
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchResponse

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun search(
        @QueryMap options: Map<String, String>
    ): VacanciesSearchResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun searchVacancyById(@Path("vacancy_id") vacancyId: Int): VacancySearchResponse

    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun searchSimilarVacancies(
        @Path("vacancy_id") vacancyId: Int,
        @QueryMap options: Map<String, String>
    ): VacanciesSearchResponse
}