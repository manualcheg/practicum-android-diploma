package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class GetChosenIndustryUseCaseImpl(private val filtersRepository: FiltersRepository) :
    GetChosenIndustryUseCase {
    override fun execute(): IndustryFilter? {
        return filtersRepository.getChosenIndustry()
    }
}