package ru.practicum.android.diploma.filter.ui.viewModel

import android.util.Log
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

    var countryFilterFromSafeArgs: CountryFilter? = null
    var areaFilterFromSafeArgs: AreaFilter? = null

    init {
        initializeCountryAndArea()
        loadFilterOptions()
    }

    fun loadFilterOptions() {
        if (countryFilter != null) {
            setCountryState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapCountryFilterToCountryUi(
                        countryFilter!!
                    ).name
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

    fun updateFromSafeArgs() {
        Log.e("MyTag", "update 1 $countryFilter\n$countryFilterFromSafeArgs")
        if (!countryFilter?.name.equals(countryFilterFromSafeArgs?.name)) {
            Log.e("MyTag", "update  2")
            countryFilter = countryFilterFromSafeArgs
            setCountryState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapCountryFilterToCountryUi(
                        countryFilterFromSafeArgs!!
                    ).name
                )
            )
        }

        if (areaFilter?.name != areaFilterFromSafeArgs?.name) {
            areaFilter = areaFilterFromSafeArgs
            setRegionState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapAreaFilterToRegionIndustryUi(
                        areaFilterFromSafeArgs!!
                    ).name
                )
            )
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