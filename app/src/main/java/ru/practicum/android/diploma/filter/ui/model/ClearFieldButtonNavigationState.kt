package ru.practicum.android.diploma.filter.ui.model

sealed interface ClearFieldButtonNavigationState {
    object ClearField : ClearFieldButtonNavigationState
    object Navigate : ClearFieldButtonNavigationState
}