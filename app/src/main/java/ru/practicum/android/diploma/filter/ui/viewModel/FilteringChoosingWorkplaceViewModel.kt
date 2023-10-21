package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.useCase.AddAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddCountryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState

class FilteringChoosingWorkplaceViewModel(
    private val addAreaFilterUseCase: AddAreaFilterUseCase,
    private val addCountryFilterUseCase: AddCountryFilterUseCase,
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
) : ViewModel() {

    private val _countryState = MutableLiveData<FilterFieldsState>()
    val countryState: LiveData<FilterFieldsState> = _countryState

    private val _regionState = MutableLiveData<FilterFieldsState>()
    val regionState: LiveData<FilterFieldsState> = _regionState

    var countryFilter: CountryFilter? = null
    var areaFilter: AreaFilter? = null

    init {
        initializeCountryAndArea()
        loadFilterOptions()
    }

    fun loadFilterOptions() {

        if (countryFilter != null) {
            setCountryState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapCountryFilterToCountryUi(countryFilter!!).name
                )
            )
        } else {
            setCountryState(
                FilterFieldsState.Empty
            )
        }

        if (areaFilter != null) {
            setRegionState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapAreaFilterToRegionIndustryUi(areaFilter!!).name
                )
            )
        } else {
            setRegionState(FilterFieldsState.Empty)
        }
    }

    fun addAreaFilter(area: AreaFilter) {
        addAreaFilterUseCase.execute(area)
    }

    fun addCountryFilter(country: CountryFilter) {
        addCountryFilterUseCase.execute(country)
    }

    private fun initializeCountryAndArea() {
        val filter = getFilterOptionsUseCase.execute()
        countryFilter = filter?.country
        areaFilter = filter?.area
    }

    private fun setCountryState(state: FilterFieldsState) {
        _countryState.value = state
    }

    private fun setRegionState(state: FilterFieldsState) {
        _regionState.value = state
    }
}