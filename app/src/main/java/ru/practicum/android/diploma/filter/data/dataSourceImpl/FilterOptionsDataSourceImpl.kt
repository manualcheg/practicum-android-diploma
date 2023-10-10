package ru.practicum.android.diploma.filter.data.dataSourceImpl

import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource

class FilterOptionsDataSourceImpl : FilterOptionsDataSource {
    override fun getFilterOptions(): HashMap<String, String> {
        return HashMap<String, String>()
    }

    override fun addFilterOptions(options: HashMap<String, String>) {

    }
}