package ru.practicum.android.diploma.favorites.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

/*class AddToFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    AddToFavoritesUseCase {
    override suspend fun execute(vacancy: Vacancy) {
        favoritesRepository.addToFavorites(vacancy)
    }
}*/

/*class DeleteFromFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    DeleteFromFavoritesUseCase {
    override suspend fun execute(id: Int) {
        favoritesRepository.deleteFromFavorites(id)
    }
}*/

class GetFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    GetFavoritesUseCase {
    override suspend fun execute(): Flow<List<Vacancy>> {
        return favoritesRepository.getFavorites()
    }
}

/*class GetVacancyUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    GetVacancyUseCase {
    override suspend fun execute(id: Int): Vacancy {
        return favoritesRepository.getVacancy(id)
    }
}*/

class AddOrDelVacancyUseCaseImpl(private val favoritesRepository: FavoritesRepository, private val vacancyRepository: VacancyRepository) :
    AddOrDelVacancyUseCase {
    override suspend fun execute(id: Int) {
        favoritesRepository.apply {
            if (isVacancyContains(id)) {
                deleteFromFavorites(id)
            } else {
//                addToFavorites(vacancy = favoritesRepository.getVacancy(id))
                vacancyRepository.findVacancyById(id).data?.let { addToFavorites(vacancy = it) }
            }
        }
    }
}

class CheckInFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    CheckInFavoritesUseCase {
    override suspend fun execute(id: Int):Boolean {
        return favoritesRepository.isVacancyContains(id)
    }
}
