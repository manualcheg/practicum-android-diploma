package ru.practicum.android.diploma.vacancy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.useCase.AddOrDelVacancyUseCase
import ru.practicum.android.diploma.favorites.domain.useCase.AddOrDelVacancyUseCaseImpl
import ru.practicum.android.diploma.favorites.domain.useCase.CheckInFavoritesUseCase
import ru.practicum.android.diploma.favorites.domain.useCase.CheckInFavoritesUseCaseImpl
import ru.practicum.android.diploma.vacancy.data.repositoryImpl.VacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCaseImpl
import ru.practicum.android.diploma.vacancy.ui.viewModel.VacancyViewModel

val vacancyModule = module {

    single<VacancyRepository> { VacancyRepositoryImpl(get(), get(), get(), get()) }
    single<FindVacancyByIdUseCase> { FindVacancyByIdUseCaseImpl(get(), get()) }
    single<AddOrDelVacancyUseCase> { AddOrDelVacancyUseCaseImpl(get(), get()) }
    single<CheckInFavoritesUseCase> { CheckInFavoritesUseCaseImpl(get()) }

    viewModel { VacancyViewModel(get(), get(), get()) }

}