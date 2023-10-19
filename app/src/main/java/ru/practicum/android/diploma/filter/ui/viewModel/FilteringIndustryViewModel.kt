package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCase

class FilteringIndustryViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
) : ViewModel() {

    init{
        viewModelScope.launch {
            getIndustriesUseCase.execute().collect { pair ->

            }
        }
    }
}