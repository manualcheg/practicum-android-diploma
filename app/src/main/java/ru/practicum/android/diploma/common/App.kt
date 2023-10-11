package ru.practicum.android.diploma.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.favorites.di.favoritesDataModule
import ru.practicum.android.diploma.favorites.di.favoritesDomainModule
import ru.practicum.android.diploma.favorites.di.favoritesUiModule
import ru.practicum.android.diploma.search.di.searchDataModule
import ru.practicum.android.diploma.search.di.searchDomainModule
import ru.practicum.android.diploma.search.di.searchUiModule
import ru.practicum.android.diploma.similar_vacancy.di.similarVacancyDataModule
import ru.practicum.android.diploma.similar_vacancy.di.similarVacancyDomainModule
import ru.practicum.android.diploma.similar_vacancy.di.similarVacancyUiModule
import ru.practicum.android.diploma.vacancy.di.vacancyDataModule
import ru.practicum.android.diploma.vacancy.di.vacancyDomainModule
import ru.practicum.android.diploma.vacancy.di.vacancyUiModule


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    searchDataModule,
                    searchDomainModule,
                    searchUiModule,
                    favoritesDataModule,
                    favoritesDomainModule,
                    favoritesUiModule,
                    vacancyDataModule,
                    vacancyDomainModule,
                    vacancyUiModule,
                    similarVacancyDataModule,
                    similarVacancyDomainModule,
                    similarVacancyUiModule
                )
            )
        }
    }
}