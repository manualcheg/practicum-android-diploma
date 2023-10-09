package ru.practicum.android.diploma.search.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface SearchUseCase {
    fun search(text: String, page: Int): Flow<Pair<Vacancies?, ErrorStatusDomain?>>
}