package ru.practicum.android.diploma.filter.data.dataSourceImpl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.data.dataSource.FilterOptionsDataSource
import ru.practicum.android.diploma.filter.data.mapper.FilterDBConverter
import ru.practicum.android.diploma.filter.data.model.dto.Filter

class FilterOptionsDataSourceImpl(
    private val sharedPrefs: SharedPreferences,
    private val filterDBConverter: FilterDBConverter
) : FilterOptionsDataSource {
    override fun getFilterOptions(): HashMap<String, String> {
        val filterFromSharedPrefs = sharedPrefs.getString("FILTER", "")

        return filterDBConverter.map(Gson().fromJson(filterFromSharedPrefs, Filter::class.java))
    }

    override fun addFilterOptions(options: HashMap<String, String>) {

    }
}