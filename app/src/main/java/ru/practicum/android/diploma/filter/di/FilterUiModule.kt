package ru.practicum.android.diploma.filter.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringAreaViewModel
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringCountryViewModel
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringIndustryViewModel
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringSettingsViewModel

val filterUiModule = module{
    viewModelOf(::FilteringSettingsViewModel)
    viewModelOf(::FilteringAreaViewModel)
    viewModelOf(::FilteringCountryViewModel)
    viewModelOf(::FilteringIndustryViewModel)
}