package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class GetFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    GetFavoritesUseCase {
    override suspend fun execute(): Flow<List<Vacancy>> {
        return favoritesRepository.getFavorites()
    }
}

class AddOrDelVacancyUseCaseImpl(private val favoritesRepository: FavoritesRepository, private val vacancyRepository: VacancyRepository) :
    AddOrDelVacancyUseCase {
    override suspend fun execute(id: Int): Boolean {
        favoritesRepository.apply {
            return if (isVacancyContainsOnce(id)) {
                deleteFromFavorites(id)
                false
            } else {
                vacancyRepository.findVacancyById(id).data?.let { addToFavorites(vacancy = it) }
                true
            }
        }
    }
}

class CheckInFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    CheckInFavoritesUseCase {
    override suspend fun execute(id: Int):Flow<Boolean> {
        return favoritesRepository.isVacancyContainsFlow(id)
    }
}
