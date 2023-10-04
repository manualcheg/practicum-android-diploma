package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.dataSourceImpl.VacancyRemoteDataSourceImpl
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.search.data.network.HeadHunterApiService
import ru.practicum.android.diploma.search.data.repositoryImpl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

val searchDataModule = module {

    single<HeadHunterApiService> {
        Retrofit.Builder().baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(HeadHunterApiService::class.java)
    }

    singleOf(::VacancyRemoteDataSourceImpl) { bind<VacancyRemoteDataSource>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    factoryOf(::VacancyDtoConverter)
}