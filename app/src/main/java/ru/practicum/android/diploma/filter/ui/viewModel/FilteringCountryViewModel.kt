package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCase

class FilteringCountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
) : ViewModel() {

    init{
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { pair ->

            }
        }
    }
}