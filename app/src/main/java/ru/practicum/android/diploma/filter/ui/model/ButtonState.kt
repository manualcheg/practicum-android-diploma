package ru.practicum.android.diploma.filter.ui.model

sealed interface ButtonState {
    object Visible : ButtonState
    object Gone : ButtonState
}