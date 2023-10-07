package ru.practicum.android.diploma.favorites.domain

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

sealed interface FavoritesState {
    data class Content(val vacancies: List<Vacancy>) : FavoritesState
    class Empty : FavoritesState
    class Error: FavoritesState
}