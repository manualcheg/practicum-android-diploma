package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class GetFilterOptionsUseCaseImpl(private val filterRepository: FilterRepository) :
    GetFilterOptionsUseCase {
    override fun execute(): Filter? {
        return filterRepository.getFilterOptions()
    }
}