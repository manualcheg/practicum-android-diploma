package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class IsTempFilterOptionsEmptyUseCaseImpl(private val filtersRepository: FiltersRepository) :
    IsTempFilterOptionsEmptyUseCase {
    override fun execute(): Boolean {
        return filtersRepository.isTempFilterOptionsEmpty()
    }
}