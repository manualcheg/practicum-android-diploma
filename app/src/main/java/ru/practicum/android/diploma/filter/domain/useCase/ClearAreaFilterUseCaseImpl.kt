package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class ClearAreaFilterUseCaseImpl(private val filtersRepository: FiltersRepository):ClearAreaFilterUseCase {
    override fun execute() {
        filtersRepository.clearArea()
    }
}