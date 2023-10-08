package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class FindVacancyByIdUseCaseImpl(
    private val repository: VacancyRepository,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter
) :
    FindVacancyByIdUseCase {
    override suspend fun findVacancyById(vacancyId: Int): Pair<VacancyUi?, ErrorStatusDomain?> {
        when (val result = repository.findVacancyById(vacancyId)) {
            is Resource.Success -> {
                return Pair(result.data?.let { vacancyDomainToVacancyUiConverter.map(it) }, null)
            }

            is Resource.Error -> {
                val errorStatusDomain = when (result.errorStatus) {
                    ErrorRemoteDataSource.NO_CONNECTION -> ErrorStatusDomain.NO_CONNECTION
                    ErrorRemoteDataSource.ERROR_OCCURRED -> ErrorStatusDomain.ERROR_OCCURRED
                    else -> null
                }
                return Pair(null, errorStatusDomain)
            }
        }
    }
}