package ru.practicum.android.diploma.similar_vacancy.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.mapper.VacancyDtoConverter
import ru.practicum.android.diploma.similar_vacancy.domain.repository.SimilarVacancyRepository

class SimilarVacancyRepositoryImpl(
    private val vacancyRemoteDataSource: VacancyRemoteDataSource,
    private val vacancyDbConverter: VacancyDtoConverter,
) : SimilarVacancyRepository {
    override fun searchSimilarVacanciesById(
        id: Int, page: Int, perPage: Int
    ): Flow<Resource<Vacancies>> {
        TODO("Not yet implemented")
    }
}