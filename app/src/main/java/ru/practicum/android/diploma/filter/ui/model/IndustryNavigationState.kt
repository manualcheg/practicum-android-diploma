package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter

sealed interface IndustryNavigationState {
    class NavigateWithContent(val industryFilter: IndustryFilter?) : IndustryNavigationState
    object NavigateEmpty : IndustryNavigationState
}
