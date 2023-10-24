package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class SetFilterToTempUseCaseImpl(private val filtersRepository: FiltersRepository) :
    SetFilterToTempUseCase {
    override fun execute(filter: Filter?) {
        filtersRepository.setFilterOptionsToCache(filter)
    }
}