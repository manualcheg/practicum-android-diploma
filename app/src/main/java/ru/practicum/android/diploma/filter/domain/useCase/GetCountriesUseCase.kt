package ru.practicum.android.diploma.filter.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Countries
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface GetCountriesUseCase {
    fun execute(): Flow<Pair<Countries?, ErrorStatusDomain?>>
}