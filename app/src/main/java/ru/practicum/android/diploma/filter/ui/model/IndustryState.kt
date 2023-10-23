package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

sealed interface IndustryState {
    object Loading : IndustryState

    sealed class Success(open val industryList: List<IndustryUi>) : IndustryState {
        data class Content(
            override val industryList: List<IndustryUi>
        ) : Success(industryList)
    }

    data class Error(
        val errorStatus: ErrorStatusUi
    ) : IndustryState
}