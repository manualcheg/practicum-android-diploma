package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.search.domain.repository.NetworkConnectionProvider
import ru.practicum.android.diploma.search.domain.useCase.GetFilteringOptionsUseCase
import ru.practicum.android.diploma.search.domain.useCase.GetFilteringOptionsUseCaseImpl
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCaseImpl
import ru.practicum.android.diploma.search.ui.NetworkConnectionProviderImpl

val searchDomainModule = module {

    singleOf(::NetworkConnectionProviderImpl) bind NetworkConnectionProvider::class
    factoryOf(::VacancyDomainToVacancyUiConverter)
    factoryOf(::SearchUseCaseImpl) bind SearchUseCase::class
    factoryOf(::GetFilteringOptionsUseCaseImpl) bind GetFilteringOptionsUseCase::class
}