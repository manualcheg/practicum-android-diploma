package ru.practicum.android.diploma.filter.ui.model

sealed interface FilterFieldsState {
    data class Empty(val hint: String) : FilterFieldsState
    data class Content(val text: String, val hint: String) : FilterFieldsState
}