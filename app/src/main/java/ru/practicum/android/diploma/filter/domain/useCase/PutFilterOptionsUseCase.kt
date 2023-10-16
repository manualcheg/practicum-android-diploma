package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter

interface PutFilterOptionsUseCase {
    fun execute(option: Filter)
}