package ru.practicum.android.diploma.similar_vacancy.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.NO_CONNECTION
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.PAGE
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.PER_PAGE
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.RESPONSE_SUCCESS
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.data.model.VacanciesSearchResponse
import ru.practicum.android.diploma.similar_vacancy.data.model.SearchSimilarVacancyRequest
import ru.practicum.android.diploma.similar_vacancy.domain.repository.SimilarVacancyRepository

class SimilarVacancyRepositoryImpl(
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDbConverter: VacancyDtoConverter,
) : SimilarVacancyRepository {
    override fun searchSimilarVacanciesById(
        id: Int, page: Int, perPage: Int
    ): Flow<Resource<Vacancies>> = flow {
        val options = HashMap<String, String>()
        options[PAGE] = page.toString()
        options[PER_PAGE] = perPage.toString()

        val response = vacancyRemoteDataSource.doRequest(
            SearchSimilarVacancyRequest(
                vacancyId = id,
                options = options
            )
        )

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
}