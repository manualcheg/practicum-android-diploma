package ru.practicum.android.diploma.similar_vacancy.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface SearchSimilarVacanciesByIdUseCase {
    fun execute(
        vacancyId: Int, page: Int, perPage: Int
    ): Flow<Pair<Vacancies?, ErrorStatusDomain?>>
}