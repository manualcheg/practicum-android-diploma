package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter

class FilterLocalCacheImpl : FilterLocalCache {
    private var filterCache: Filter? = null
    override fun getFilterCache(): Filter? {
        return filterCache
    }

    override fun addCountry(country: CountryFilter) {
        filterCache = Filter(
            country = country,
            area = filterCache?.area,
            industry = filterCache?.industry,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
    }

    override fun addArea(area: AreaFilter) {
        filterCache = Filter(
            country = filterCache?.country,
            area = area,
            industry = filterCache?.industry,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
    }

    override fun addIndustry(industry: IndustryFilter) {
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
        setFilterCacheNullIfFilterCacheIsEmpty()
    }

    override fun clearAll() {
        filterCache = null
    }

    override fun clearArea() {
        filterCache = Filter(
            country = filterCache?.country,
            area = null,
            industry = filterCache?.industry,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
        setFilterCacheNullIfFilterCacheIsEmpty()
    }

    override fun clearIndustry() {
        filterCache = Filter(
            country = filterCache?.country,
            area = filterCache?.area,
            industry = null,
            salary = filterCache?.salary,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
        setFilterCacheNullIfFilterCacheIsEmpty()
    }

    override fun clearSalary() {
        filterCache = Filter(
            country = filterCache?.country,
            area = filterCache?.area,
            industry = filterCache?.industry,
            salary = null,
            onlyWithSalary = filterCache?.onlyWithSalary ?: false
        )
        setFilterCacheNullIfFilterCacheIsEmpty()
    }

    private fun setFilterCacheNullIfFilterCacheIsEmpty() {
        val area = filterCache?.area
        val country = filterCache?.country
        val industry = filterCache?.industry
        val salary = filterCache?.salary
        val onlyWithSalary = filterCache?.onlyWithSalary ?: false

        if (area == null && country == null && industry == null && salary == null && !onlyWithSalary) {
            filterCache = null
        }
    }
}