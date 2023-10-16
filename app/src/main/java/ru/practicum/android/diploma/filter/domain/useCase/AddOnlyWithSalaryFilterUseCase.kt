package ru.practicum.android.diploma.filter.domain.useCase


interface AddOnlyWithSalaryFilterUseCase {
    fun execute(option: Boolean)
}