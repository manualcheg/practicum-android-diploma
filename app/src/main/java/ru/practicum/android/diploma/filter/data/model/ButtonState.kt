package ru.practicum.android.diploma.filter.data.model

sealed interface ButtonState {
    object Visible : ButtonState
    object Gone : ButtonState
}