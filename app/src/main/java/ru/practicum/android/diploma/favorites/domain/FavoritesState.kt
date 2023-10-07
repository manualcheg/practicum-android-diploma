package ru.practicum.android.diploma.favorites.domain

import ru.practicum.android.diploma.common.ui.model.VacancyUi

sealed interface FavoritesState {
    data class Content(val vacancies: List<VacancyUi>) : FavoritesState
    object Empty : FavoritesState
    object Error : FavoritesState
}