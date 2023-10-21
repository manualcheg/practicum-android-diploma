package ru.practicum.android.diploma.filter.data.dataSource

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter

interface FiltersLocalDataSource {
    fun getFilterOptions(): Filter?
    fun putFilterOptions(options: Filter)
    fun addCountry(country: CountryFilter)
    fun addArea(area: AreaFilter)
    fun addIndustry(industry: IndustryFilter)
    fun addSalary(salary: Int)
    fun addOnlyWithSalary(option: Boolean)
    fun clearFilterOptions()
    fun clearArea()
    fun clearIndustry()
    fun clearSalary()
}