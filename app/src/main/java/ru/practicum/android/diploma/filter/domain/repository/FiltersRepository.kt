package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.common.util.Resource

interface FiltersRepository {

    fun getAreas(areaId: Int?): Flow<Resource<List<AreaFilter>>>
    fun getCountries(): Flow<Resource<List<CountryFilter>>>
    fun getIndustries(): Flow<Resource<List<IndustryFilter>>>
}