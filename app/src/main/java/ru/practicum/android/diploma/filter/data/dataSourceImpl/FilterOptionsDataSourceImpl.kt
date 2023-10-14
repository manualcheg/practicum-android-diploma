package ru.practicum.android.diploma.filter.data.dataSourceImpl

import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.filter.data.db.FilterDataBase
import ru.practicum.android.diploma.filter.data.db.FilterLocalCache
import ru.practicum.android.diploma.filter.data.model.dto.Area
import ru.practicum.android.diploma.filter.data.model.dto.Country
import ru.practicum.android.diploma.filter.data.model.dto.Filter
import ru.practicum.android.diploma.filter.data.model.dto.Industry

class FilterOptionsDataSourceImpl(
    private val filterDataBase: FilterDataBase,
    private val filterLocalCache: FilterLocalCache
) : FilterOptionsDataSource {
    override fun getFilterOptions(): Filter? {
        return if (filterLocalCache.getFilterCache() == null) {
            filterDataBase.getFilterOptions()
        } else {
            filterLocalCache.getFilterCache()
        }
    filterLocalCache.clear()
    }

    override fun putFilterOptions(options: Filter) {
        filterDataBase.putFilterOptions(options)
    }

    override fun addCountry(country: Country) {
        filterLocalCache.addCountry(country)
    }

    override fun addArea(area: Area) {
        filterLocalCache.addArea(area)
    }

    override fun addIndustry(industry: Industry) {
        filterLocalCache.addIndustry(industry)
    }

    override fun addSalary(salary: Int) {
        filterLocalCache.addSalary(salary)
    }

    override fun addOnlyWithSalary(option: Boolean) {
        filterLocalCache.addOnlyWithSalary(option)
    }

    override fun clearFilterOptions() {
        filterDataBase.clearSavedFilter()
        filterLocalCache.clear()
    }
}