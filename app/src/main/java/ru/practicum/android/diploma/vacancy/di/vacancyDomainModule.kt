package ru.practicum.android.diploma.vacancy.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.domain.useCase.AddVacancyToFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.AddVacancyToFavoritesUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.useCase.CallPhoneUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.CallPhoneUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.useCase.CheckInFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.CheckInFavoritesUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.useCase.DeleteVacancyFromFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.DeleteVacancyFromFavoritesUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.useCase.OpenMailUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.OpenMailUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.useCase.ShareVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.ShareVacancyByIdUseCaseImpl

val vacancyDomainModule = module {

    factoryOf(::FindVacancyByIdUseCaseImpl) bind FindVacancyByIdUseCase::class
    factoryOf(::CheckInFavoritesUseCaseImpl) bind CheckInFavoritesUseCase::class

    factoryOf(::AddVacancyToFavoritesUseCaseImpl) bind AddVacancyToFavoritesUseCase::class
    factoryOf(::DeleteVacancyFromFavoritesUseCaseImpl) bind DeleteVacancyFromFavoritesUseCase::class

    factoryOf(::OpenMailUseCaseImpl) bind OpenMailUseCase::class
    factoryOf(::ShareVacancyByIdUseCaseImpl) bind ShareVacancyByIdUseCase::class
    factoryOf(::CallPhoneUseCaseImpl) bind CallPhoneUseCase::class
}