package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class AddIndustryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    AddIndustryFilterUseCase {
    override fun execute(industry: IndustryFilter) {
        filtersRepository.addIndustry(industry)
    }
}