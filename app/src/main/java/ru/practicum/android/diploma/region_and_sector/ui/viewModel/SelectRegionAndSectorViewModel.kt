package ru.practicum.android.diploma.region_and_sector.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.useCase.AddAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddCountryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddIndustryFilterUseCase

class SelectRegionAndSectorViewModel(
    private val addAreaFilterUseCase: AddAreaFilterUseCase,
    private val addCountryFilterUseCase: AddCountryFilterUseCase,
    private val addIndustryFilterUseCase: AddIndustryFilterUseCase,
) : ViewModel() {
}