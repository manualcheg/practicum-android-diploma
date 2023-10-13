package ru.practicum.android.diploma.filter.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.common.util.constants.FilterConst.JOBSEEKER_SHAREDPREFS
import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.filter.data.dataSourceImpl.FilterOptionsDataSourceImpl
import ru.practicum.android.diploma.filter.data.repositoryImpl.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.filter.data.mapper.FilterDBConverter

val filterDataModule = module {

    factory<SharedPreferences> {
        androidContext().getSharedPreferences(JOBSEEKER_SHAREDPREFS, Context.MODE_PRIVATE)
    }

    factoryOf(::FilterDBConverter)
    singleOf(::FilterOptionsDataSourceImpl) bind FilterOptionsDataSource::class
    singleOf(::FilterRepositoryImpl) bind FilterRepository::class
}