package ru.practicum.android.diploma.filter.ui.model

sealed interface SelectButtonState {
    object Visible : SelectButtonState
    object Invisible : SelectButtonState
}