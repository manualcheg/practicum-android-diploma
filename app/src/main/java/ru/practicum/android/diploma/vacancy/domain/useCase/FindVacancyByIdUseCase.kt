package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

interface FindVacancyByIdUseCase {
    suspend fun findVacancyById(vacancyId: Int): Pair<VacancyUi?, ErrorStatusDomain?>
}