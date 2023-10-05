package ru.practicum.android.diploma.search.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface SearchUseCase {
    fun search(expression: String): Flow<Pair<List<VacancyUi>?, ErrorStatusDomain?>>
}