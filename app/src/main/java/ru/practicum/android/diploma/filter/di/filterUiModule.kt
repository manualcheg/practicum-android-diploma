package ru.practicum.android.diploma.filter.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringSettingsViewModel

val filterUiModule = module{
    viewModelOf(::FilteringSettingsViewModel)
}