package ru.practicum.android.diploma.search.domain.useCase

interface GetFilteringOptionsUseCase {
    fun execute(): HashMap<String, String>
}