package ru.practicum.android.diploma.search.domain.useCase

import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class IsFiltersExistsUseCaseImpl(private val searchRepository: SearchRepository) :
    IsFiltersExistsUseCase {
    override fun execute(): Boolean {
        return searchRepository.isFiltersExist()
    }
}