package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.filter.data.model.dto.Area
import ru.practicum.android.diploma.filter.data.model.dto.Country
import ru.practicum.android.diploma.filter.data.model.dto.Filter
import ru.practicum.android.diploma.filter.data.model.dto.Industry

class LocalCacheImpl(override val filterCache: Filter) : LocalCache {
    override fun addCountry(country: Country) {
        TODO("Not yet implemented")
    }

    override fun addArea(area: Area) {
        TODO("Not yet implemented")
    }

    override fun addIndustry(industry: Industry) {
        TODO("Not yet implemented")
    }

    override fun addSalary(salary: Int) {
        TODO("Not yet implemented")
    }

    override fun addOnlyWithSalary(option: Boolean) {
        TODO("Not yet implemented")
    }
}