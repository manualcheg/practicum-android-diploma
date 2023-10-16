package ru.practicum.android.diploma.similar_vacancy.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.similar_vacancy.domain.repository.SimilarVacancyRepository

class SearchSimilarVacanciesByIdUseCaseImpl(private val repository: SimilarVacancyRepository) :
    SearchSimilarVacanciesByIdUseCase {
    override fun execute(
        vacancyId: Int, page: Int, perPage: Int
    ): Flow<Pair<Vacancies?, ErrorStatusDomain?>> {
        return repository.searchSimilarVacanciesById(vacancyId, page, perPage).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    val errorStatusDomain = when (result.errorStatus) {
                        ErrorRemoteDataSource.NO_CONNECTION -> ErrorStatusDomain.NO_CONNECTION
                        ErrorRemoteDataSource.ERROR_OCCURRED -> ErrorStatusDomain.ERROR_OCCURRED
                        else -> null
                    }
                    Pair(null, errorStatusDomain)
                }
            }
        }
    }
}