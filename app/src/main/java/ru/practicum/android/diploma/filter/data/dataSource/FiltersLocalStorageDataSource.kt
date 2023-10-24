package ru.practicum.android.diploma.filter.data.dataSource

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter

interface FiltersLocalStorageDataSource {
    fun getFilterOptions(): Filter?
    fun setFilterOptions(filters: Filter)
    fun clearFilterOptions()
}