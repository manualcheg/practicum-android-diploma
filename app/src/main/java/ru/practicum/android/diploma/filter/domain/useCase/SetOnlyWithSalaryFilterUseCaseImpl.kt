package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class SetOnlyWithSalaryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    SetOnlyWithSalaryFilterUseCase {
    override fun execute(option: Boolean) {
        filtersRepository.setOnlyWithSalary(option)
    }
}