package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry

class FilterLocalCacheImpl : FilterLocalCache {
    private var filterCache: Filter? = null
    override fun getFilterCache(): Filter? {
        return filterCache
    }

    override fun addCountry(country: Country) {
        filterCache = Filter(
            country,
            filterCache?.area,
            filterCache?.industry,
            filterCache?.salary,
            filterCache?.onlyWithSalary
        )
    }

    override fun addArea(area: Area) {
        filterCache = Filter(
            filterCache?.country,
            area,
            filterCache?.industry,
            filterCache?.salary,
            filterCache?.onlyWithSalary
        )
    }

    override fun addIndustry(industry: Industry) {
        filterCache = Filter(
            filterCache?.country,
            filterCache?.area,
            industry,
            filterCache?.salary,
            filterCache?.onlyWithSalary
        )
    }

    override fun addSalary(salary: Int) {
        filterCache = Filter(
            filterCache?.country,
            filterCache?.area,
            filterCache?.industry,
            salary,
            filterCache?.onlyWithSalary
        )
    }

    override fun addOnlyWithSalary(option: Boolean) {
        filterCache = Filter(
            filterCache?.country,
            filterCache?.area,
            filterCache?.industry,
            filterCache?.salary,
            option
        )
    }

    override fun clear() {
        filterCache = null
    }
}