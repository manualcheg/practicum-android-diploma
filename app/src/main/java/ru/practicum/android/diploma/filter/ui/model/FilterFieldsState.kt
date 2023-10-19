package ru.practicum.android.diploma.filter.ui.model

sealed interface FilterFieldsState {
    object Empty : FilterFieldsState
    data class Content(val text: String) : FilterFieldsState
}