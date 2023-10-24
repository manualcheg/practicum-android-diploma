package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter

sealed interface AreaNavigationState {
    class NavigateWithContent(val areaFilter: AreaFilter) : AreaNavigationState
    object NavigateEmpty : AreaNavigationState
}