package ru.practicum.android.diploma.search.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.search.ui.viewModel.SearchViewModel

val searchUiModule = module {

    factoryOf(::VacancyDomainToVacancyUiConverter)
    viewModelOf(::SearchViewModel)
}