package ru.practicum.android.diploma.similar_vacancy.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.similar_vacancy.domain.useCase.SearchSimilarVacanciesByIdUseCase
import ru.practicum.android.diploma.similar_vacancy.domain.useCase.SearchSimilarVacanciesByIdUseCaseImpl

val similarVacancyDomainModule = module {
    factoryOf(::SearchSimilarVacanciesByIdUseCaseImpl) bind (SearchSimilarVacanciesByIdUseCase::class)
}