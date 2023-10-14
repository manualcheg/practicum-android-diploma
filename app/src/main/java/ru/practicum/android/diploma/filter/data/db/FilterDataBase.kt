package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.filter.data.model.dto.Filter

interface FilterDataBase {
    fun getFilterOptions(): Filter?
    fun putFilterOptions(filter:Filter)
    fun clearSavedFilter()
}