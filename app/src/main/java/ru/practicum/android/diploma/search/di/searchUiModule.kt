package ru.practicum.android.diploma.search.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.search.ui.viewModel.SearchViewModel

val searchUiModule = module {

    viewModelOf(::SearchViewModel)
}