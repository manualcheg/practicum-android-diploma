package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class AddCountryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    AddCountryFilterUseCase {
    override fun execute(country: CountryFilter) {
        filtersRepository.addCountry(country)
    }
}