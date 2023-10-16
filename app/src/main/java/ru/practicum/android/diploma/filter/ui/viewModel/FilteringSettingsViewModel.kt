package ru.practicum.android.diploma.filter.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository

class FilteringSettingsViewModel(
    val repository: FiltersRepository
) : ViewModel() {
    fun init() {
        viewModelScope.launch {
            repository.getAreas(null).collect { resource ->
                resource.data?.forEach {
                    Log.d("judjin", it.toString())
                }
            }
        }
    }
}