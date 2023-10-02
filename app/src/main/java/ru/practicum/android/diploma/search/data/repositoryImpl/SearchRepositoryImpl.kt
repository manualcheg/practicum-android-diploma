package ru.practicum.android.diploma.search.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.models.Vacancy
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.search.data.model.dto.SearchRequest
import ru.practicum.android.diploma.search.data.model.dto.SearchResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

// Класс SearchRepositoryImpl - реализация интерфейса SearchRepository
// Задача этой реализации — сделать запрос и получить ответ от сервера, используя сетевой клиент
class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDbConverter: VacancyDbConverter
) : SearchRepository {

    override fun search(expression: String): Flow<Resource<List<Vacancy>>> = flow {

        val response = networkClient.doRequest(SearchRequest(expression))

        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error(response.resultCode)) // "Проверьте подключение к интернету"
            }

            200 -> {
                val vacancies: List<Vacancy> = (response as SearchResponse).items.map {
                    vacancyDbConverter.map(it)
                }
                emit(Resource.Success(vacancies))
            }

            else -> {
                emit(Resource.Error(response.resultCode)) // другая ошибка
            }
        }

    }

}