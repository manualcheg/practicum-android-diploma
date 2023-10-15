package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry

interface FilterRepository {
    fun addCountry(country: Country)
    fun addArea(area: Area)
    fun addIndustry(industry: Industry)
    fun addSalary(salary: Int)
    fun addOnlyWithSalary(option: Boolean)
    fun getFilterOptions(): Filter?
    fun putFilterOptions(options: Filter)
    fun clearFilterOptions()

}