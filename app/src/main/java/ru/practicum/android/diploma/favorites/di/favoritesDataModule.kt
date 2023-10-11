package ru.practicum.android.diploma.favorites.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.common.util.constants.DataBaseConst.VACANCIES_FAVORITE
import ru.practicum.android.diploma.favorites.data.dataSource.FavoritesLocalDataSource
import ru.practicum.android.diploma.favorites.data.dataSourceImpl.FavoritesLocalDataSourceImpl
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.favorites.data.repositoryImpl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

val favoritesDataModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDataBase::class.java,
            name = VACANCIES_FAVORITE
        ).build()
    }
    factoryOf(::VacancyDbConverter)
    singleOf(::FavoritesLocalDataSourceImpl) bind FavoritesLocalDataSource::class
    singleOf(::FavoritesRepositoryImpl) bind FavoritesRepository::class
}
