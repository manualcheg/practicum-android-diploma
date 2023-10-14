package ru.practicum.android.diploma.filter.data.dataSourceImpl

import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.filter.data.db.FilterDb
import ru.practicum.android.diploma.filter.data.db.LocalCache
import ru.practicum.android.diploma.filter.data.model.dto.Area
import ru.practicum.android.diploma.filter.data.model.dto.Country
import ru.practicum.android.diploma.filter.data.model.dto.Filter
import ru.practicum.android.diploma.filter.data.model.dto.Industry

class FilterOptionsDataSourceImpl(
    private val filterDb: FilterDb,
    private val localCache: LocalCache
) : FilterOptionsDataSource {
    override fun getFilterOptions(): Filter? {
        return if (localCache.getFilterCache() == null) {
            filterDb.getFilterOptions()
        } else {
            localCache.getFilterCache()
        }
    }

    override fun putFilterOptions(options: Filter) {
        filterDb.putFilterOptions(options)
    }

    override fun addCountry(country: Country) {
        localCache.addCountry(country)
    }

    override fun addArea(area: Area) {
        localCache.addArea(area)
    }

    override fun addIndustry(industry: Industry) {
        localCache.addIndustry(industry)
    }

    override fun addSalary(salary: Int) {
        localCache.addSalary(salary)
    }

    override fun addOnlyWithSalary(option: Boolean) {
        localCache.addOnlyWithSalary(option)
    }

    override fun clearFilterOptions() {
        filterDb.clearSavedFilter()
        localCache.clear()
    }
}