package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class SetAreaFilterUseCaseImpl(private val filtersRepository: FiltersRepository) :
    SetAreaFilterUseCase {
    override fun execute(area: AreaFilter) {

        filtersRepository.setArea(area)
    }
}