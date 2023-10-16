package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class AddCountryFilterUseCaseImpl(private val filterRepository: FilterRepository) :
    AddCountryFilterUseCase {
    override fun execute(country: Country) {
        filterRepository.addCountry(country)
    }
}