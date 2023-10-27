package ru.practicum.android.diploma.filter.ui.model

sealed interface ChoosingWorkplaceState {

    sealed class CountryAndArea(open val country: String?, open val area: String?) :
        ChoosingWorkplaceState {
        object EmptyCountryEmptyArea : CountryAndArea(null, null)
        data class ContentCountryEmptyArea(override val country: String) :
            CountryAndArea(country, null)

        data class EmptyCountryContentArea(override val area: String) :
            CountryAndArea(null, area)

        data class ContentCountryContentArea(
            override val country: String,
            override val area: String
        ) :
            CountryAndArea(country, area)
    }
}
