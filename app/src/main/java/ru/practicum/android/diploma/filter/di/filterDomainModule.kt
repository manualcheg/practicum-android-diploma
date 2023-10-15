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
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCaseImpl
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCaseImpl

val filterDomainModule = module {

    factoryOf(::AddAreaFilterUseCaseImpl) bind AddAreaFilterUseCase::class
    factoryOf(::AddCountryFilterUseCaseImpl) bind AddCountryFilterUseCase::class
    factoryOf(::AddIndustryFilterUseCaseImpl) bind AddIndustryFilterUseCase::class
    factoryOf(::AddOnlyWithSalaryFilterUseCaseImpl) bind AddOnlyWithSalaryFilterUseCase::class
    factoryOf(::AddSalaryFilterUseCaseImpl) bind AddSalaryFilterUseCase::class
    factoryOf(::ClearFilterOptionsUseCaseImpl) bind ClearFilterOptionsUseCase::class
    factoryOf(::GetFilterOptionsUseCaseImpl) bind GetFilterOptionsUseCase::class
    factoryOf(::PutFilterOptionsUseCaseImpl) bind PutFilterOptionsUseCase::class

}