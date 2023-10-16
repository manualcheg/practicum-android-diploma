package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class AddSalaryFilterUseCaseImpl(private val filterRepository: FilterRepository) :
    AddSalaryFilterUseCase {
    override fun execute(salary: Int) {
        filterRepository.addSalary(salary)
    }
}