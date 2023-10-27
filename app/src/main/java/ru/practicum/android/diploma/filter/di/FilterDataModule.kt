package ru.practicum.android.diploma.filter.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.common.util.constants.FilterConst.JOBSEEKER_SHARED_PREFS
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalDataSource
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalStorageDataSource
import ru.practicum.android.diploma.filter.data.dataSourceImpl.FiltersLocalDataSourceImpl
import ru.practicum.android.diploma.filter.data.dataSourceImpl.FiltersLocalStorageDataSourceImpl
import ru.practicum.android.diploma.filter.data.db.FilterDataBase
import ru.practicum.android.diploma.filter.data.db.FilterDataBaseImpl
import ru.practicum.android.diploma.filter.data.db.FilterLocalCache
import ru.practicum.android.diploma.filter.data.db.FilterLocalCacheImpl
import ru.practicum.android.diploma.filter.data.mapper.FiltersDtoToDomainConverter
import ru.practicum.android.diploma.filter.data.repositoryImpl.FiltersRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.AreasRepository
import ru.practicum.android.diploma.filter.data.repositoryImpl.AreasRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.WorkplaceRepository
import ru.practicum.android.diploma.filter.data.repositoryImpl.WorkplaceRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.CountryRepository
import ru.practicum.android.diploma.filter.data.repositoryImpl.CountryRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository
import ru.practicum.android.diploma.filter.domain.repository.IndustryRepository
import ru.practicum.android.diploma.filter.data.repositoryImpl.IndustryRepositoryImpl
import ru.practicum.android.diploma.search.domain.mapper.FilterToOptionsConverter

val filterDataModule = module {

    factory<SharedPreferences> {
        androidContext().getSharedPreferences(JOBSEEKER_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    factoryOf(::FilterToOptionsConverter)
    singleOf(::FiltersLocalDataSourceImpl) bind FiltersLocalDataSource::class
    singleOf(::FiltersLocalStorageDataSourceImpl) bind FiltersLocalStorageDataSource::class
    singleOf(::FiltersRepositoryImpl) bind FiltersRepository::class
    singleOf(::AreasRepositoryImpl) bind AreasRepository::class
    singleOf(::WorkplaceRepositoryImpl) bind WorkplaceRepository::class
    singleOf(::CountryRepositoryImpl) bind CountryRepository::class
    singleOf(::IndustryRepositoryImpl) bind IndustryRepository::class
    singleOf(::FilterDataBaseImpl) bind FilterDataBase::class
    singleOf(::FilterLocalCacheImpl) bind FilterLocalCache::class
    factoryOf(::FiltersDtoToDomainConverter)
}