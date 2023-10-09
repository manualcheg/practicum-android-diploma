package ru.practicum.android.diploma.filter.data.dataSource

interface FilterOptionsDataSource {
    fun getFilterOptions(): HashMap<String, String>
    fun addFilterOptions(options: HashMap<String, String>)
}