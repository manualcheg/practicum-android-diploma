package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.filter.data.model.dto.Area
import ru.practicum.android.diploma.filter.data.model.dto.Country
import ru.practicum.android.diploma.filter.data.model.dto.Filter
import ru.practicum.android.diploma.filter.data.model.dto.Industry

interface LocalCache {
    val filterCache: Filter
    fun addCountry(country: Country)
    fun addArea(area: Area)
    fun addIndustry(industry: Industry)
    fun addSalary(salary: Int)
    fun addOnlyWithSalary(option: Boolean)
}