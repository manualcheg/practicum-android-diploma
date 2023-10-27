package ru.practicum.android.diploma.filter.ui.model


sealed class ChoosingWorkplaceState(open val country: String?, open val area: String?) {
    object EmptyCountryEmptyArea : ChoosingWorkplaceState(null, null)
    data class ContentCountryEmptyArea(override val country: String) :
        ChoosingWorkplaceState(country, null)

    data class EmptyCountryContentArea(override val area: String) :
        ChoosingWorkplaceState(null, area)

    data class ContentCountryContentArea(
        override val country: String,
        override val area: String
    ) :
        ChoosingWorkplaceState(country, area)
}
