package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class AddSalaryFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    AddSalaryFilterUseCase {
    override fun execute(salary: Int) {
        filtersRepository.addSalary(salary)
    }
}