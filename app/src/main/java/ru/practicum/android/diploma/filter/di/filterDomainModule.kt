package ru.practicum.android.diploma.filter.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.domain.useCase.AddAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddAreaFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.AddCountryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddCountryFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.AddIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddIndustryFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.AddOnlyWithSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddOnlyWithSalaryFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.AddSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddSalaryFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.ClearAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearAreaFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.ClearIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearIndustryFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.ClearSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearSalaryFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCaseImpl

val filterDomainModule = module {
    factoryOf(::AddAreaFilterUseCaseImpl) bind AddAreaFilterUseCase::class
    factoryOf(::AddCountryFilterUseCaseImpl) bind AddCountryFilterUseCase::class
    factoryOf(::AddIndustryFilterUseCaseImpl) bind AddIndustryFilterUseCase::class
    factoryOf(::AddOnlyWithSalaryFilterUseCaseImpl) bind AddOnlyWithSalaryFilterUseCase::class
    factoryOf(::AddSalaryFilterUseCaseImpl) bind AddSalaryFilterUseCase::class
    factoryOf(::ClearFilterOptionsUseCaseImpl) bind ClearFilterOptionsUseCase::class
    factoryOf(::GetAreasUseCaseImpl) bind GetAreasUseCase::class
    factoryOf(::GetCountriesUseCaseImpl) bind GetCountriesUseCase::class
    factoryOf(::GetFilterOptionsUseCaseImpl) bind GetFilterOptionsUseCase::class
    factoryOf(::GetIndustriesUseCaseImpl) bind GetIndustriesUseCase::class
    factoryOf(::PutFilterOptionsUseCaseImpl) bind PutFilterOptionsUseCase::class
    factoryOf(::ClearAreaFilterUseCaseImpl) bind ClearAreaFilterUseCase::class
    factoryOf(::ClearIndustryFilterUseCaseImpl) bind ClearIndustryFilterUseCase::class
    factoryOf(::ClearSalaryFilterUseCaseImpl) bind ClearSalaryFilterUseCase::class
}
