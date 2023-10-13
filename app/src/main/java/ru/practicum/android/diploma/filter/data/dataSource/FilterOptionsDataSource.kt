package ru.practicum.android.diploma.filter.data.dataSource

import ru.practicum.android.diploma.filter.data.model.dto.Filter

interface FilterOptionsDataSource {
    fun getFilterOptions(): Filter
    fun putFilterOptions(options: Filter)
}