package ru.practicum.android.diploma.filter.domain.useCase

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.filter.domain.repository.AreasRepository

class SetAreaFilterUseCaseImpl(private val areasRepository: AreasRepository) :
    SetAreaFilterUseCase {
    override fun execute(area: AreaFilter?) {
        areasRepository.setArea(area)
    }
}