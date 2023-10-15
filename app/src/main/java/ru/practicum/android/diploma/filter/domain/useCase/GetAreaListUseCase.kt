package ru.practicum.android.diploma.filter.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface GetAreaListUseCase {
    fun execute(country: Country?): Flow<Pair<Areas?, ErrorStatusDomain?>>
}