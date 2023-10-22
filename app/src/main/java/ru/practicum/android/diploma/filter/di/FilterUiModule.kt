package ru.practicum.android.diploma.filter.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.mapper.AreaFilterDomainToRegionCountryUiConverter
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringAreaViewModel
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringSettingsViewModel

val filterUiModule = module {
    factoryOf(::FilterDomainToFilterUiConverter)
    factoryOf(::AreaFilterDomainToRegionCountryUiConverter)
    viewModelOf(::FilteringSettingsViewModel)
    viewModel { (parentId: String?) ->
        FilteringAreaViewModel(
            parentId = parentId,
            getRegionsUseCase = get(),
            areaFilterDomainToRegionCountryUiConverter = get(),
        )
    }
}