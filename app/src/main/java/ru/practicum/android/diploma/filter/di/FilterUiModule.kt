package ru.practicum.android.diploma.filter.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringSettingsViewModel

val filterUiModule = module {
    factoryOf(::FilterDomainToFilterUiConverter)
    viewModelOf(::FilteringSettingsViewModel)
    viewModel { (country: CountryFilter, area: AreaFilter) ->
        FilteringChoosingWorkplaceViewModel(
            get(),
            get(),
            get(),
            get(),
            country,
            area
        )
    }
}