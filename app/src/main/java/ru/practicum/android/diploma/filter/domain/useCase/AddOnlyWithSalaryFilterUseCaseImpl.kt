package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class AddOnlyWithSalaryFilterUseCaseImpl(private val filterRepository: FilterRepository) :
    AddOnlyWithSalaryFilterUseCase {
    override fun execute(option: Boolean) {
        filterRepository.addOnlyWithSalary(option)
    }
}