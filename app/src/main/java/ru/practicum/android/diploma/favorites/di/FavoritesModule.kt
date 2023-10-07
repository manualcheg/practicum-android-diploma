package ru.practicum.android.diploma.favorites.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesLocalDataSource
import ru.practicum.android.diploma.favorites.data.dataSourceImpl.FavoritesLocalDataSourceImpl
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.favorites.data.repositoryImpl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.favorites.domain.useCase.GetFavoritesUseCase
import ru.practicum.android.diploma.favorites.domain.useCase.GetFavoritesUseCaseImpl
import ru.practicum.android.diploma.favorites.ui.viewModel.FavoritesViewModel

val favoritesModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDataBase::class.java,
            name = "vacancies_favorites"
        ).build()
    }

    factory { VacancyDbConverter() }

    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    single<FavoritesLocalDataSource> { FavoritesLocalDataSourceImpl(get(), get()) }

    viewModel{
        FavoritesViewModel(get())
    }

    single<GetFavoritesUseCase>{ GetFavoritesUseCaseImpl(get())}
}