package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class SetSalaryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    SetSalaryFilterUseCase {
    override fun execute(salary: Int) {
        filtersRepository.setSalary(salary)
    }
}