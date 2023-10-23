package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class IsTempFilterOptionsExistsUseCaseImpl(private val filtersRepository: FiltersRepository) :
    IsTempFilterOptionsExistsUseCase {
    override fun execute(): Boolean {
        return filtersRepository.isTempFilterOptionsExists()
    }
}