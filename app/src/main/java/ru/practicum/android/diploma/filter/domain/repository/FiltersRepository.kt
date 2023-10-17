package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Countries
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.common.util.Resource

interface FiltersRepository {

    fun getAreas(areaId: Int?): Flow<Resource<Areas>>
    fun getCountries(): Flow<Resource<Countries>>
    fun getIndustries(): Flow<Resource<Industries>>
    fun addCountry(country: CountryFilter)
    fun addArea(area: AreaFilter)
    fun addIndustry(industry: IndustryFilter)
    fun addSalary(salary: Int)
    fun addOnlyWithSalary(option: Boolean)
    fun getFilterOptions(): Filter?
    fun putFilterOptions(options: Filter)
    fun clearFilterOptions()
}