package ru.practicum.android.diploma.filter.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCaseImpl

val filterDomainModule = module {
    factoryOf(::GetAreasUseCaseImpl) bind GetAreasUseCase::class
    factoryOf(::GetCountriesUseCaseImpl) bind GetCountriesUseCase::class
    factoryOf(::GetIndustriesUseCaseImpl) bind GetIndustriesUseCase::class
}