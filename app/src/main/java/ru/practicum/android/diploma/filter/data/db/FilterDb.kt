package ru.practicum.android.diploma.filter.data.db

import ru.practicum.android.diploma.filter.data.model.dto.Filter

interface FilterDb {
    fun getFilterOptions(): Filter
    fun putFilterOptions(filter:Filter)
}