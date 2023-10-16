package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.useCase.AddSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddOnlyWithSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCase

class FilteringSettingsViewModel(
    private val addSalaryFilterUseCase: AddSalaryFilterUseCase,
    private val addOnlyWithSalaryFilterUseCase: AddOnlyWithSalaryFilterUseCase,
    private val clearFilterOptionsUseCase: ClearFilterOptionsUseCase,
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val putFilterOptionsUseCase: PutFilterOptionsUseCase,
) : ViewModel() {
}