package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class PutFilterOptionsUseCaseImpl(private val filtersRepository: FiltersRepository) :
    PutFilterOptionsUseCase {
    override fun execute(option: Filter) {
        filtersRepository.putFilterOptions(option)
    }
}