package ru.practicum.android.diploma.filter.data.dataSourceImpl

import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.filter.data.db.FilterDb
import ru.practicum.android.diploma.filter.data.db.LocalCache
import ru.practicum.android.diploma.filter.data.model.dto.Filter

class FilterOptionsDataSourceImpl(
    private val filterDb: FilterDb,
    private val localCache: LocalCache
) : FilterOptionsDataSource {
    override fun getFilterOptions(): Filter {
        return if (localCache.filterCache.equals(null)){
            filterDb.getFilterOptions()
        } else {
            localCache.filterCache
        }

    }

    override fun putFilterOptions(options: Filter) {
        TODO("Not yet implemented")
    }
}