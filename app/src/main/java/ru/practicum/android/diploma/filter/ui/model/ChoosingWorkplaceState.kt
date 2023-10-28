package ru.practicum.android.diploma.filter.ui.model

sealed interface ChoosingWorkplaceState {
    object EmptyCountryEmptyArea : ChoosingWorkplaceState
    data class ContentCountryEmptyArea(val country: String) :
        ChoosingWorkplaceState

    data class EmptyCountryContentArea(val area: String) :
        ChoosingWorkplaceState

    data class ContentCountryContentArea(
        val country: String,
        val area: String
    ) :
        ChoosingWorkplaceState
}