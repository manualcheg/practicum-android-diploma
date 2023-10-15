package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class AddAreaFilterUseCaseImpl(private val filterRepository: FilterRepository) :
    AddAreaFilterUseCase {
    override fun execute(area: Area) {
        filterRepository.addArea(area)
    }
}