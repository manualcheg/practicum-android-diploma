package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Industry

interface AddIndustryFilterUseCase {
    fun execute(industry: Industry)
}