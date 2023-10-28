package ru.practicum.android.diploma.vacancy.ui.model

import ru.practicum.android.diploma.common.ui.model.VacancyUi

sealed class VacancyState(open var isFavorite: Boolean) {
    data class Load(override var isFavorite: Boolean) : VacancyState(isFavorite)

    data class Error(override var isFavorite: Boolean) : VacancyState(isFavorite)

    data class Content(override var isFavorite: Boolean, val vacancy: VacancyUi) : VacancyState(isFavorite)
}
