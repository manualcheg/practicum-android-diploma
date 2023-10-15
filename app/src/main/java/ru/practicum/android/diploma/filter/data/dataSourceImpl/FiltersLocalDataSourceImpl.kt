package ru.practicum.android.diploma.filter.data.dataSourceImpl

import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalDataSource
import ru.practicum.android.diploma.filter.data.db.FilterDataBase
import ru.practicum.android.diploma.filter.data.db.FilterLocalCache

class FiltersLocalDataSourceImpl(
    private val filterDataBase: FilterDataBase,
    private val filterLocalCache: FilterLocalCache
) : FiltersLocalDataSource {
    override fun getFilterOptions(): Filter? {
        return if (filterLocalCache.getFilterCache() == null) {
            filterDataBase.getFilterOptions()
        } else {
            filterLocalCache.getFilterCache()
        }
    }

    override fun putFilterOptions(options: Filter) {
        filterDataBase.putFilterOptions(options)
        filterLocalCache.clear()
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