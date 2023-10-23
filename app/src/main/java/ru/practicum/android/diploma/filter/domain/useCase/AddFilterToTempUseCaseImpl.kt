package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class AddFilterToTempUseCaseImpl(private val filtersRepository: FiltersRepository) :
    AddFilterToTempUseCase {
    override fun execute(filter: Filter?) {
        filtersRepository.addFilterToTemp(filter)
    }
}