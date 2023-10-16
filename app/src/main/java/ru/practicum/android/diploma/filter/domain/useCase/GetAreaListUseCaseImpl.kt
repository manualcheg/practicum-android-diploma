package ru.practicum.android.diploma.filter.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

class GetAreaListUseCaseImpl(private val filterRepository: FilterRepository) : GetAreaListUseCase {
    override fun execute(country: Country?): Flow<Pair<Areas?, ErrorStatusDomain?>> {
        if (country == null) {
            return filterRepository.getAreaListWithoutCountry().map { result ->
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
        } else {
            return filterRepository.getAreaListWithCountry(country).map { result ->
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
}