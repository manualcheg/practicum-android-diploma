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
import ru.practicum.android.diploma.filter.ui.model.SelectButtonState

class FilteringChoosingWorkplaceViewModel(
    private val addAreaFilterUseCase: AddAreaFilterUseCase,
    private val addCountryFilterUseCase: AddCountryFilterUseCase,
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
) : ViewModel() {

    var countryFilter: CountryFilter? = null
    private var areaFilter: AreaFilter? = null

    private val _countryState = MutableLiveData<FilterFieldsState>()
    val countryState: LiveData<FilterFieldsState> = _countryState

    private val _regionState = MutableLiveData<FilterFieldsState>()
    val regionState: LiveData<FilterFieldsState> = _regionState

    private val _selectButtonState = MutableLiveData<SelectButtonState>()
    val selectButtonState: LiveData<SelectButtonState> = _selectButtonState


    init {
        initializeCountryAndArea()
        updateCountryField(countryFilter)
        updateAreaField(areaFilter)
        updateSelectButton()
    }

    fun updateCountryField(countryFilter: CountryFilter?) {

        if (countryFilter != null) {
            setCountryState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapCountryFilterToCountryUi(countryFilter).name
                )
            )
        } else {
            setCountryState(
                FilterFieldsState.Empty
            )
        }
        this.countryFilter = countryFilter
    }

    fun updateAreaField(areaFilter: AreaFilter?) {
        if (areaFilter != null) {
            setRegionState(
                FilterFieldsState.Content(
                    filterDomainToFilterUiConverter.mapAreaFilterToRegionIndustryUi(areaFilter).name
                )
            )
        } else {
            setRegionState(FilterFieldsState.Empty)
        }
        this.areaFilter = areaFilter
    }

    fun addAreaFilter() {
        areaFilter?.let { addAreaFilterUseCase.execute(it) }
    }

    fun addCountryFilter() {
        countryFilter?.let { addCountryFilterUseCase.execute(it) }
    }

    fun updateSelectButton() {
        if (countryFilter != null || areaFilter != null) {
            setSelectButtonState(SelectButtonState.Visible)
        } else {
            setSelectButtonState(SelectButtonState.Invisible)
        }
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

    private fun setSelectButtonState(state: SelectButtonState) {
        _selectButtonState.value = state
    }
}