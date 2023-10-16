package ru.practicum.android.diploma.vacancy.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.ui.viewModel.VacancyViewModel

val vacancyUiModule = module {
    viewModelOf(::VacancyViewModel)
}