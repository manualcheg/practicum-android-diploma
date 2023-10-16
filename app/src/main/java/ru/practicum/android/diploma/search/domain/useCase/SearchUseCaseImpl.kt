package ru.practicum.android.diploma.search.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchUseCaseImpl(
    private val repository: SearchRepository,

    ) : SearchUseCase {
    override fun search(
        text: String,
        page: Int,
        perPage: Int
    ): Flow<Pair<Vacancies?, ErrorStatusDomain?>> {
        return repository.search(text, page, perPage).map { result ->
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