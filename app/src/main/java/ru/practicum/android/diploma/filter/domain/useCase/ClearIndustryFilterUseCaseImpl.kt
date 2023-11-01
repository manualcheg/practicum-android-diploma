package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class ClearIndustryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) : ClearIndustryFilterUseCase {
    override fun execute() {
        filtersRepository.clearIndustry()
    }
}