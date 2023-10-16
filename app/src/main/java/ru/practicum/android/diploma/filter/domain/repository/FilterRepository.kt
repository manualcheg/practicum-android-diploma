package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Countries
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.common.util.Resource

interface FilterRepository {
    fun addCountry(country: Country)
    fun addArea(area: Area)
    fun addIndustry(industry: Industry)
    fun addSalary(salary: Int)
    fun addOnlyWithSalary(option: Boolean)
    fun getFilterOptions(): Filter?
    fun putFilterOptions(options: Filter)
    fun clearFilterOptions()
    fun getCountryList(): Flow<Resource<Countries>>
    fun getAreaListWithCountry(country: Country?): Flow<Resource<Areas>>
    fun getAreaListWithoutCountry(): Flow<Resource<Areas>>
    fun getIndustryList(): Flow<Resource<Industries>>

}