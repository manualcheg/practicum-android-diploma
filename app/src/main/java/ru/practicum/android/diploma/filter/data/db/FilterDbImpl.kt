package ru.practicum.android.diploma.filter.data.db

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.data.model.dto.Filter

class FilterDbImpl(private val sharedPrefs: SharedPreferences):FilterDb {
    override fun getFilterOptions(): Filter {
        val filterFromSharedPrefs = sharedPrefs.getString("FILTER", "")

        return Gson().fromJson(filterFromSharedPrefs, Filter::class.java)
    }

    override fun putFilterOptions(filter: Filter) {
        TODO("Not yet implemented")
    }
}