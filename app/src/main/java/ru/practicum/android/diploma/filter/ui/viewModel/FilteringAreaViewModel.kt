package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.GetAreasUseCase

class FilteringAreaViewModel(
    private val getRegionsUseCase: GetAreasUseCase,
) : ViewModel() {

    init{
        viewModelScope.launch {
            getRegionsUseCase.execute(null).collect { pair ->

            }
        }
    }
}