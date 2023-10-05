package ru.practicum.android.diploma.vacancy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.repositoryImpl.VacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyUseCaseImpl
import ru.practicum.android.diploma.vacancy.ui.viewModel.VacancyViewModel

val vacancyModule = module {

    viewModel { (vacancyId: Int) -> VacancyViewModel(vacancyId) }
    single<VacancyRepository> { VacancyRepositoryImpl() }
    single<FindVacancyUseCase> { FindVacancyUseCaseImpl(get()) }

}