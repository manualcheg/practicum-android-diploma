package ru.practicum.android.diploma.filter.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Area
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.constants.RepositoryConst
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalDataSource
import ru.practicum.android.diploma.filter.data.mapper.FiltersDtoToDomainConverter
import ru.practicum.android.diploma.filter.data.model.AreaRequest
import ru.practicum.android.diploma.filter.data.model.AreasResponse
import ru.practicum.android.diploma.filter.data.model.CountriesRequest
import ru.practicum.android.diploma.filter.data.model.CountriesResponse
import ru.practicum.android.diploma.filter.data.model.IndustriesRequest
import ru.practicum.android.diploma.filter.data.model.IndustriesResponse
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource

class FilterRepositoryImpl(
    private val filtersLocalDataSourceImpl: FiltersLocalDataSource,
    private val filtersRemoteDataSource: VacancyRemoteDataSource,
    private val filtersDtoToDomainConverter: FiltersDtoToDomainConverter

) : FilterRepository {
    override fun getAreas(): Flow<Resource<List<Area>>> = flow {
        val response = filtersRemoteDataSource.doRequest(AreaRequest())
        when (response.resultCode) {
            RepositoryConst.NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RepositoryConst.RESPONSE_SUCCESS -> {
                val areas: List<Area> = filtersDtoToDomainConverter.map(response as AreasResponse)
                emit(Resource.Success(areas))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = filtersRemoteDataSource.doRequest(CountriesRequest())
        when (response.resultCode) {
            RepositoryConst.NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RepositoryConst.RESPONSE_SUCCESS -> {
                val countries: List<Country> =
                    filtersDtoToDomainConverter.map(response as CountriesResponse)
                emit(Resource.Success(countries))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = filtersRemoteDataSource.doRequest(IndustriesRequest())
        when (response.resultCode) {
            RepositoryConst.NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RepositoryConst.RESPONSE_SUCCESS -> {
                val industries: List<Industry> =
                    filtersDtoToDomainConverter.map(response as IndustriesResponse)
                emit(Resource.Success(industries))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }
}