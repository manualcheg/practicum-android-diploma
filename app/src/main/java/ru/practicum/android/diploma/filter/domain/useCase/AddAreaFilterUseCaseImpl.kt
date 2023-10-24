package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class AddAreaFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    AddAreaFilterUseCase {
    override fun execute(area: AreaFilter?) {

        filtersRepository.addArea(area)
    }
}