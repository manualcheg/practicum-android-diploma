package ru.practicum.android.diploma.filter.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface GetIndustryListUseCase {
    fun execute(): Flow<Pair<Industries?, ErrorStatusDomain?>>
}