package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

sealed interface FilteringCountriesState {
    object Load : FilteringCountriesState
    data class Content(val countriesList: List<AreaCountryUi>) : FilteringCountriesState
    data class Error(val errorStatus: ErrorStatusUi) : FilteringCountriesState
}