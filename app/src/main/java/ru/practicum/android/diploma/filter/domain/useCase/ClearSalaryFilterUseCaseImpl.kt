package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class ClearSalaryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) : ClearSalaryFilterUseCase {
    override fun execute() {
        filtersRepository.clearSalary()
    }
}