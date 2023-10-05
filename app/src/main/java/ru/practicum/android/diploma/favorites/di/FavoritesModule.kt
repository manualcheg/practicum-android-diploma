package ru.practicum.android.diploma.favorites.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesStorage
import ru.practicum.android.diploma.favorites.data.dataSourceImpl.FavoritesStorageImpl
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.favorites.data.repositoryImpl.FavoritesDBRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesDBRepository
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

    single<FavoritesDBRepository> { FavoritesDBRepositoryImpl(get()) }
    single<FavoritesStorage> { FavoritesStorageImpl(get(), get()) }

    viewModel{
        FavoritesViewModel(get())
    }

    single<GetFavoritesUseCase>{ GetFavoritesUseCaseImpl(get())}
}