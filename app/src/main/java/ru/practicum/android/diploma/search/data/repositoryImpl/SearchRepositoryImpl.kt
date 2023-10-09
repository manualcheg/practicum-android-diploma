package ru.practicum.android.diploma.search.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.data.model.SearchRequest
import ru.practicum.android.diploma.search.data.model.VacanciesSearchResponse
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val filterOptionsDataSource: FilterOptionsDataSource,
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDbConverter: VacancyDtoConverter,
) : SearchRepository {

    override fun search(text: String, page: Int, perPage: Int): Flow<Resource<Vacancies>> = flow {

        val options = filterOptionsDataSource.getFilterOptions()

        options[SEARCH_TEXT] = text
        options[PAGE] = page.toString()
        options[PER_PAGE] = perPage.toString()

        val response = vacancyRemoteDataSource.doRequest(SearchRequest(options = options))

        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RESPONSE_SUCCESS -> {
                val vacancies: Vacancies =
                    vacancyDbConverter.map(response as VacanciesSearchResponse)
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
        const val SEARCH_TEXT = "text"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
    }
}