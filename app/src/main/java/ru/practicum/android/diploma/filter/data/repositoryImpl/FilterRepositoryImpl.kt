package ru.practicum.android.diploma.filter.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Area
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Countries
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.common.util.Resource
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

    override fun getCountryList(): Flow<Resource<Countries>> = flow {
        TODO("Not yet implemented")
    }

    override fun getAreaListWithCountry(country: Country?): Flow<Resource<Areas>> {
        TODO("Not yet implemented")
    }

    override fun getAreaListWithoutCountry(): Flow<Resource<Areas>> {
        TODO("Not yet implemented")
    }

    override fun getIndustryList(): Flow<Resource<Industries>> = flow {
        TODO("Not yet implemented")
    }


}