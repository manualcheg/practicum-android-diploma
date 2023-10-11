package ru.practicum.android.diploma.favorites.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.ui.viewModel.FavoritesViewModel

val favoritesUiModule = module {
    viewModelOf(::FavoritesViewModel)
}