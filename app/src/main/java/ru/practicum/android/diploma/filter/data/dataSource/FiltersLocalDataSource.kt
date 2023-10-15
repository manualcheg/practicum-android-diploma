package ru.practicum.android.diploma.filter.data.dataSource

import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry

interface FiltersLocalDataSource {
    fun getFilterOptions(): Filter?
    fun putFilterOptions(options: Filter)
    fun addCountry(country: Country)
    fun addArea(area: Area)
    fun addIndustry(industry: Industry)
    fun addSalary(salary: Int)
    fun addOnlyWithSalary(option: Boolean)
    fun clearFilterOptions()
}