package ru.practicum.android.diploma.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.vacancy.di.vacancyModule


class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            //TODO Here need to insert all modules:
            modules(
                vacancyModule,
            )
        }
    }
}