package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class ClearFilterOptionsUseCaseImpl(private val filtersRepository: FiltersRepository) :
    ClearFilterOptionsUseCase {
    override fun execute() {
        filtersRepository.clearFilterOptions()
    }
}