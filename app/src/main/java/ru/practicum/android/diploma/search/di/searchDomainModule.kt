package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.repository.NetworkConnectionProvider
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCaseImpl
import ru.practicum.android.diploma.search.ui.NetworkConnectionProviderImpl

val searchDomainModule = module {

    singleOf(::NetworkConnectionProviderImpl) { bind<NetworkConnectionProvider>() }
    factoryOf(::SearchUseCaseImpl) { bind<SearchUseCase>() }
}