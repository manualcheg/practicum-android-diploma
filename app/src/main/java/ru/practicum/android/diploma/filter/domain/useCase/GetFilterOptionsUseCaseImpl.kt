package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class GetFilterOptionsUseCaseImpl(private val filtersRepository: FiltersRepository) :
    GetFilterOptionsUseCase {
    override fun execute(): Filter? {
        return filtersRepository.getFilterOptions()
    }
}