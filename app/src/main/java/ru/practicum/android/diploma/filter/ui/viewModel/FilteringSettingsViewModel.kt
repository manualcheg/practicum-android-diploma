package ru.practicum.android.diploma.filter.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.AddSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddOnlyWithSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCase

class FilteringSettingsViewModel(
    private val addSalaryFilterUseCase: AddSalaryFilterUseCase,
    private val addOnlyWithSalaryFilterUseCase: AddOnlyWithSalaryFilterUseCase,
    private val clearFilterOptionsUseCase: ClearFilterOptionsUseCase,
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val putFilterOptionsUseCase: PutFilterOptionsUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getIndustriesUseCase: GetIndustriesUseCase,
) : ViewModel() {

    fun init() { // для проверки работы кода
        viewModelScope.launch {
            getAreasUseCase.execute(null).collect { pair ->
                pair.first?.arealList?.forEach { Log.d("judjin", it.toString()) }
            }
        }
    }
}