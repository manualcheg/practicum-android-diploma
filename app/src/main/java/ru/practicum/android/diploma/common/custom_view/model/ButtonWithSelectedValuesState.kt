package ru.practicum.android.diploma.common.custom_view.model

sealed interface ButtonWithSelectedValuesState {
    data class Empty(val hint: String) : ButtonWithSelectedValuesState
    data class Content(val text: String, val hint: String) : ButtonWithSelectedValuesState
}