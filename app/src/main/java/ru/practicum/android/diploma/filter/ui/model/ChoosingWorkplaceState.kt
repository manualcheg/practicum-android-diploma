package ru.practicum.android.diploma.filter.ui.model

sealed interface ChoosingWorkplaceState {
    sealed class Country(open val country: String?) : ChoosingWorkplaceState {
        object Empty : Country(null)
        data class Content(override val country: String) : Country(country)
    }

    sealed class Area(open val area: String?) : ChoosingWorkplaceState {
        object Empty : Area(null)
        data class Content(override val area: String) : Area(area)
    }

    sealed interface SelectButton : ChoosingWorkplaceState {
        object Visible : SelectButton
        object Gone : SelectButton
    }

}
