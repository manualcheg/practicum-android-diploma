package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class AddOnlyWithSalaryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    AddOnlyWithSalaryFilterUseCase {
    override fun execute(option: Boolean) {
        filtersRepository.addOnlyWithSalary(option)
    }
}