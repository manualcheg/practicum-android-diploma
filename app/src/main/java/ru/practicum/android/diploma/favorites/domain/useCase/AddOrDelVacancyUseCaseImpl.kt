package ru.practicum.android.diploma.favorites.domain.useCase

import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

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