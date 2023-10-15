package ru.practicum.android.diploma.filter.data.repositoryImpl

import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class FilterRepositoryImpl(private val filterOptionsDataSourceImpl: FilterOptionsDataSource) :
    FilterRepository {
    override fun addCountry(country: Country) {
        filterOptionsDataSourceImpl.addCountry(country)
    }

    override fun addArea(area: Area) {
        filterOptionsDataSourceImpl.addArea(area)
    }

    override fun addIndustry(industry: Industry) {
        filterOptionsDataSourceImpl.addIndustry(industry)
    }

    override fun addSalary(salary: Int) {
        filterOptionsDataSourceImpl.addSalary(salary)
    }

    override fun addOnlyWithSalary(option: Boolean) {
        filterOptionsDataSourceImpl.addOnlyWithSalary(option)
    }

    override fun getFilterOptions(): Filter? {
        return filterOptionsDataSourceImpl.getFilterOptions()
    }

    override fun putFilterOptions(options: Filter) {
        filterOptionsDataSourceImpl.putFilterOptions(options)
    }

    override fun clearFilterOptions() {
        filterOptionsDataSourceImpl.clearFilterOptions()
    }

}