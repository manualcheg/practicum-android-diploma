package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.mapper.FilterToOptionsConverter
import ru.practicum.android.diploma.search.domain.repository.NetworkConnectionProvider
import ru.practicum.android.diploma.search.domain.useCase.IsFiltersExistsUseCase
import ru.practicum.android.diploma.search.domain.useCase.IsFiltersExistsUseCaseImpl
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCaseImpl
import ru.practicum.android.diploma.search.ui.NetworkConnectionProviderImpl

val searchDomainModule = module {
    singleOf(::NetworkConnectionProviderImpl) bind NetworkConnectionProvider::class
    factoryOf(::SearchUseCaseImpl) bind SearchUseCase::class
    factoryOf(::FilterToOptionsConverter)
    factoryOf(::IsFiltersExistsUseCaseImpl) bind IsFiltersExistsUseCase::class
}