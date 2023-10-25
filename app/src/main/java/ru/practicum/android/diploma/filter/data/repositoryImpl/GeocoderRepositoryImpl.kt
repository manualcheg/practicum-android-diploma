package ru.practicum.android.diploma.filter.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.filter.data.dataSource.GeocoderRemoteDataSource
import ru.practicum.android.diploma.filter.domain.model.MyLocation
import ru.practicum.android.diploma.filter.domain.repository.GeocoderRepository

class GeocoderRepositoryImpl(
    geocoderRemoteDataSourceImpl: GeocoderRemoteDataSource,
    filterRemoteDataSource: GeocoderRemoteDataSource
) : GeocoderRepository {
    override fun getLocation(coordinates: String): Flow<Resource<MyLocation>> = flow {

    }
}