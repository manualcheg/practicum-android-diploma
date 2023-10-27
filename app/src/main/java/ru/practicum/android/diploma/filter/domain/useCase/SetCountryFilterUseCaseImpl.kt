package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.repository.CountryRepository

class SetCountryFilterUseCaseImpl(private val countryRepository: CountryRepository) :
    SetCountryFilterUseCase {
    override fun execute(country: CountryFilter) {
        countryRepository.setCountry(country)
    }
}