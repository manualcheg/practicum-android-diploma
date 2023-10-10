package ru.practicum.android.diploma.similar_vacancy.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.similar_vacancy.data.repositoryImpl.SimilarVacancyRepositoryImpl
import ru.practicum.android.diploma.similar_vacancy.domain.repository.SimilarVacancyRepository

val similarVacancyDataModule = module {

    singleOf(::SimilarVacancyRepositoryImpl) bind (SimilarVacancyRepository::class)
}