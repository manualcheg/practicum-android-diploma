package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter

interface FilterDataBase {
    fun getFilterOptions(): Filter?
    fun putFilterOptions(filter: Filter)
    fun clearSavedFilter()
}