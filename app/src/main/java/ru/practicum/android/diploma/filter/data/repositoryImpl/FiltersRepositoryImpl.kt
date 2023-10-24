package ru.practicum.android.diploma.filter.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Countries
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.NO_CONNECTION
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.RESPONSE_SUCCESS
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalDataSource
import ru.practicum.android.diploma.filter.data.mapper.FiltersDtoToDomainConverter
import ru.practicum.android.diploma.filter.data.model.AllAreasRequest
import ru.practicum.android.diploma.filter.data.model.AreasResponse
import ru.practicum.android.diploma.filter.data.model.CertainAreasRequest
import ru.practicum.android.diploma.filter.data.model.CountriesRequest
import ru.practicum.android.diploma.filter.data.model.CountriesResponse
import ru.practicum.android.diploma.filter.data.model.IndustriesRequest
import ru.practicum.android.diploma.filter.data.model.IndustriesResponse
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource

class FiltersRepositoryImpl(
    private val filtersLocalDataSourceImpl: FiltersLocalDataSource,
    private val filtersRemoteDataSource: VacancyRemoteDataSource,
    private val filtersDtoToDomainConverter: FiltersDtoToDomainConverter
) : FiltersRepository {
    override fun getAreas(areaId: String?): Flow<Resource<Areas>> = flow {

        val responseAreas = if (areaId == null) {
            filtersRemoteDataSource.doRequest(AllAreasRequest())
        } else filtersRemoteDataSource.doRequest(CertainAreasRequest(areaId))

        val responseCountries = filtersRemoteDataSource.doRequest(CountriesRequest())

        if (responseAreas.resultCode == NO_CONNECTION || responseCountries.resultCode == NO_CONNECTION) {
            emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))

        } else if (responseAreas.resultCode == RESPONSE_SUCCESS || responseCountries.resultCode == RESPONSE_SUCCESS) {

            val areas: List<AreaFilter> =
                filtersDtoToDomainConverter.convertAreaResponseToListOfAreaWithoutCountries(
                    responseAreas as AreasResponse, responseCountries as CountriesResponse
                )
            emit(Resource.Success(Areas(areas)))

        } else emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
    }

    override fun getCountries(): Flow<Resource<Countries>> = flow {
        val response = filtersRemoteDataSource.doRequest(CountriesRequest())
        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RESPONSE_SUCCESS -> {
                val countries: List<CountryFilter> =
                    filtersDtoToDomainConverter.convertCountriesResponseToListOfCountries(response as CountriesResponse)
                emit(Resource.Success(Countries(countries)))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun getIndustries(): Flow<Resource<Industries>> = flow {
        val response = filtersRemoteDataSource.doRequest(IndustriesRequest())
        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RESPONSE_SUCCESS -> {
                val industries: List<IndustryFilter> =
                    filtersDtoToDomainConverter.convertIndustryResponseToListOfIndustries(response as IndustriesResponse)
                emit(Resource.Success(Industries(industries)))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun addCountry(country: CountryFilter) {
        filtersLocalDataSourceImpl.addCountry(country)
    }

    override fun addArea(area: AreaFilter?) {
        filtersLocalDataSourceImpl.addArea(area)
    }

    override fun addIndustry(industry: IndustryFilter) {
        filtersLocalDataSourceImpl.addIndustry(industry)
    }

    override fun addSalary(salary: Int) {
        filtersLocalDataSourceImpl.addSalary(salary)
    }

    override fun addOnlyWithSalary(option: Boolean) {
        filtersLocalDataSourceImpl.addOnlyWithSalary(option)
    }

    override fun getFilterOptions(): Filter? {
        return filtersLocalDataSourceImpl.getFilterOptions()
    }

    override fun putFilterOptions(options: Filter) {
        filtersLocalDataSourceImpl.putFilterOptions(options)
    }

    override fun clearFilterOptions() {
        filtersLocalDataSourceImpl.clearFilterOptions()
    }

    override fun clearArea() {
        filtersLocalDataSourceImpl.clearArea()
    }

    override fun clearIndustry() {
        filtersLocalDataSourceImpl.clearIndustry()
    }

    override fun clearSalary() {
        filtersLocalDataSourceImpl.clearSalary()
    }

    override fun clearTempFilterOptions() {
        filtersLocalDataSourceImpl.clearTempFilterOptions()
    }

    override fun isTempFilterOptionsEmpty(): Boolean {
        return filtersLocalDataSourceImpl.isTempFilterOptionsEmpty()
    }

    override fun isTempFilterOptionsExists(): Boolean {
        return filtersLocalDataSourceImpl.isTempFilterOptionsExists()
    }

    override fun addFilterToTemp(filter: Filter?) {
        filtersLocalDataSourceImpl.addFilterToCache(filter)
    }
}