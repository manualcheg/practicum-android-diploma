package ru.practicum.android.diploma.search.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchUseCaseImpl(
    private val repository: SearchRepository,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter
) : SearchUseCase {
    override fun search(expression: String): Flow<Pair<List<VacancyUi>?, ErrorStatusDomain?>> {
        return repository.search(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data?.map { vacancyDomainToVacancyUiConverter.map(it) }
                    Pair(data, null)
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