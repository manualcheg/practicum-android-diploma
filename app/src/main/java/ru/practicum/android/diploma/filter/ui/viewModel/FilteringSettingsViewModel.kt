package ru.practicum.android.diploma.filter.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCase

class FilteringSettingsViewModel(
    private val getAreasUseCase: GetAreasUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getIndustriesUseCase: GetIndustriesUseCase,
) : ViewModel() {
    fun init() { // для проверки работы кода
        viewModelScope.launch {
            getIndustriesUseCase.execute().collect { pair ->
                pair.first?.industryList?.forEach { Log.d("judjin", it.toString()) }
            }
        }
    }
}
