package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

sealed interface IndustryState {
    object Loading : IndustryState

    sealed class Success : IndustryState {
        data class Content(
            val industryList: List<IndustryUi>
        ) : Success()
    }

    data class Error(
        val errorStatus: ErrorStatusUi
    ) : IndustryState
}