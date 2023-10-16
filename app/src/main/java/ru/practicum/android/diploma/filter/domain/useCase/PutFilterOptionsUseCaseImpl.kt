package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class PutFilterOptionsUseCaseImpl(private val filterRepository: FilterRepository) :
    PutFilterOptionsUseCase {
    override fun execute(option: Filter) {
        filterRepository.putFilterOptions(option)
    }
}