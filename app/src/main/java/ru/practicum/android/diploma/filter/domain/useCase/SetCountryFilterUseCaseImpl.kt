package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class SetCountryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    SetCountryFilterUseCase {
    override fun execute(country: CountryFilter) {
        filtersRepository.setCountry(country)
    }
}