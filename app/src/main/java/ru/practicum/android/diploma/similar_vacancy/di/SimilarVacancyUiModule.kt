package ru.practicum.android.diploma.similar_vacancy.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.similar_vacancy.ui.viewModel.SimilarVacancyViewModel

val similarVacancyUiModule = module {
    viewModelOf(::SimilarVacancyViewModel)
}