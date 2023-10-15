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
            country = country,
            area = filterCache?.area,
            industry = filterCache?.industry,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
    }

    override fun addArea(area: Area) {
        filterCache = Filter(
            country = filterCache?.country,
            area = area,
            industry = filterCache?.industry,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
    }

    override fun addIndustry(industry: Industry) {
        filterCache = Filter(
            country = filterCache?.country,
            area = filterCache?.area,
            industry = industry,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
    }

    override fun addSalary(salary: Int) {
        filterCache = Filter(
            country = filterCache?.country,
            area = filterCache?.area,
            industry = filterCache?.industry,
            salary = salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
    }

    override fun addOnlyWithSalary(isOnlyWithSalary: Boolean) {
        filterCache = Filter(
            country = filterCache?.country,
            area = filterCache?.area,
            industry = filterCache?.industry,
            salary = filterCache?.salary,
            onlyWithSalary = isOnlyWithSalary
        )
    }

    override fun clear() {
        filterCache = null
    }
}