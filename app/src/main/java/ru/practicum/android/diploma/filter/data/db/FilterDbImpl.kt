package ru.practicum.android.diploma.filter.data.db

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.common.util.constants.FilterConst.FILTER
import ru.practicum.android.diploma.filter.data.model.dto.Filter

class FilterDbImpl(private val sharedPrefs: SharedPreferences) : FilterDb {
    override fun getFilterOptions(): Filter? {
        val options = sharedPrefs.getString(FILTER, "")
        return if (options == "") {
            null
        } else {
            Gson().fromJson(options, Filter::class.java)
        }
    }

    override fun putFilterOptions(filter: Filter) {
        sharedPrefs.edit().putString(FILTER, Gson().toJson(filter)).apply()
    }

    override fun clearSavedFilter() {
        sharedPrefs.edit().clear().apply()
    }
}