package ru.practicum.android.diploma.search.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.data.model.SearchRequest
import ru.practicum.android.diploma.search.data.model.SearchResponse
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDbConverter: VacancyDtoConverter
) : SearchRepository {

    override fun search(options: HashMap<String, String>): Flow<Resource<List<Vacancy>>> = flow {

        val response = vacancyRemoteDataSource.doRequest(SearchRequest(options = options))

        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RESPONSE_SUCCESS -> {
                val vacancies: List<Vacancy> = (response as SearchResponse).items.map {
                    vacancyDbConverter.map(it)
                }
                emit(Resource.Success(vacancies))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    companion object {
        const val NO_CONNECTION = -1
        const val RESPONSE_SUCCESS = 200
    }
}