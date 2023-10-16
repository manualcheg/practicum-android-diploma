package ru.practicum.android.diploma.vacancy.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.repositoryImpl.VacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.ExternalNavigator
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository
import ru.practicum.android.diploma.vacancy.ui.navigator.ExternalNavigatorImpl

val vacancyDataModule = module {

    singleOf(::ExternalNavigatorImpl) bind ExternalNavigator::class
    singleOf(::VacancyRepositoryImpl) bind VacancyRepository::class
}