package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class AddIndustryFilterUseCaseImpl(private val filterRepository: FilterRepository) :
    AddIndustryFilterUseCase {
    override fun execute(industry: Industry) {
        filterRepository.addIndustry(industry)
    }
}