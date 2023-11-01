package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class SetFilterOptionsUseCaseImpl(private val filtersRepository: FiltersRepository) :
    SetFilterOptionsUseCase {
    override fun execute(option: Filter) {
        filtersRepository.setFilterOptionsToStorage(option)
    }
}