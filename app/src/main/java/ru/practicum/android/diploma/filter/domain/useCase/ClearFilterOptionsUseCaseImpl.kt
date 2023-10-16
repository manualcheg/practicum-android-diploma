package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class ClearFilterOptionsUseCaseImpl(private val filterRepository: FilterRepository) :
    ClearFilterOptionsUseCase {
    override fun execute() {
        filterRepository.clearFilterOptions()
    }
}