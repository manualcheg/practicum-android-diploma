package ru.practicum.android.diploma.vacancy.data.repositoryImpl

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.data.db.AppDataBase
import ru.practicum.android.diploma.favorites.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
) : VacancyRepository {
    override suspend fun findVacancy(vacancyId: Int): Vacancy {
        TODO()
    }

    override suspend fun getVacancyFromDataBaseById(vacancyId: Int): Vacancy {
        return vacancyDbConverter.map(appDataBase.FavoritesVacanciesDao().getVacancy(vacancyId))
    }
}