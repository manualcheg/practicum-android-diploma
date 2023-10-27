package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.util.Resource

interface AreasRepository{
    fun getAreas(areaId: String?): Flow<Resource<Areas>>
    fun setArea(area: AreaFilter?)
}