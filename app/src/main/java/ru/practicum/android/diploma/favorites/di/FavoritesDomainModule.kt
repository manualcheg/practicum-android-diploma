package ru.practicum.android.diploma.favorites.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.useCase.GetFavoritesUseCase
import ru.practicum.android.diploma.favorites.domain.useCase.GetFavoritesUseCaseImpl

val favoritesDomainModule = module {
    singleOf(::GetFavoritesUseCaseImpl) bind GetFavoritesUseCase::class
}