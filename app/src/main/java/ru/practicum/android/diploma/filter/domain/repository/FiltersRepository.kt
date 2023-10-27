package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter

interface FiltersRepository {

//    fun getAreas(areaId: String?): Flow<Resource<Areas>>
//    fun getCountries(): Flow<Resource<Countries>>
//    fun getIndustries(): Flow<Resource<Industries>>
//    fun setCountry(country: CountryFilter)
//    fun setArea(area: AreaFilter?)
//    fun setIndustry(industry: IndustryFilter)
    fun setSalary(salary: Int)
    fun setOnlyWithSalary(option: Boolean)
    fun getFilterOptions(): Filter?
    fun setFilterOptionsToStorage(options: Filter)
    fun clearFilterOptions()
    fun clearArea()
    fun clearIndustry()
    fun clearSalary()
    fun clearTempFilterOptions()
    fun isTempFilterOptionsEmpty(): Boolean
    fun isTempFilterOptionsExists(): Boolean

    fun setFilterOptionsToCache(filter: Filter?)

//    fun getChosenIndustry(): IndustryFilter?
//    fun getChosenArea(): AreaFilter?
//    fun getChosenCountry(): CountryFilter?
}