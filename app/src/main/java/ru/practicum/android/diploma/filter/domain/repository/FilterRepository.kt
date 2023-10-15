package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Area
import ru.practicum.android.diploma.common.util.Resource

interface FilterRepository {

    fun getAreas(): Flow<Resource<List<Area>>>
    fun getCountries(): Flow<Resource<List<Country>>>
    fun getIndustries(): Flow<Resource<List<Industry>>>
}