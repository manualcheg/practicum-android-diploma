package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class GetChosenAreaUseCaseImpl(private val filtersRepository: FiltersRepository) :
    GetChosenAreaUseCase {
    override fun execute(): AreaFilter? {
        return filtersRepository.getChosenArea()
    }
}