package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

sealed interface AreasState {
    object Loading : AreasState

    sealed class Success(open val arealList: List<RegionCountryUi>) : AreasState {
        data class Content(
            override val arealList: List<RegionCountryUi>
        ) : Success(arealList)
    }

    data class Error(
        val errorStatus: ErrorStatusUi
    ) : AreasState

}