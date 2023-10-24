package ru.practicum.android.diploma.search.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.NO_CONNECTION
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.PAGE
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.PER_PAGE
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.RESPONSE_SUCCESS
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.SEARCH_TEXT
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalStorageDataSource
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.data.model.SearchRequest
import ru.practicum.android.diploma.search.data.model.VacanciesSearchResponse
import ru.practicum.android.diploma.search.domain.mapper.FilterToOptionsConverter
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val filtersLocalStorageDataSource: FiltersLocalStorageDataSource,
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDtoToDomainConverter: VacancyDtoConverter,
    private val filterToOptionsConverter: FilterToOptionsConverter
) : SearchRepository {

    override fun search(text: String, page: Int, perPage: Int): Flow<Resource<Vacancies>> = flow {

        val filters = filtersLocalStorageDataSource.getFilterOptions()
        val options = filterToOptionsConverter.map(filters)

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
                    vacancyDtoToDomainConverter.mapVacanciesSearchResponseToVacancies(response as VacanciesSearchResponse)
                emit(Resource.Success(vacancies))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun isFiltersExist(): Boolean {
        return filtersLocalStorageDataSource.getFilterOptions() != null
    }
}