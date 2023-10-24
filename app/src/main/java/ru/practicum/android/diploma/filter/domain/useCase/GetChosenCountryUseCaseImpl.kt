package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class GetChosenCountryUseCaseImpl(private val filtersRepository: FiltersRepository) :
    GetChosenCountryUseCase {
    override fun execute(): CountryFilter? {
        return filtersRepository.getChosenCountry()
    }
}