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
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState

class FilteringChoosingWorkplaceViewModel(
    private val addAreaFilterUseCase: AddAreaFilterUseCase,
    private val addCountryFilterUseCase: AddCountryFilterUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
) : ViewModel() {

    var countryFilter: CountryFilter? = null
    private var areaFilter: AreaFilter? = null

    private val _countryState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    val countryState: LiveData<FilterFieldsState> = _countryState

    private val _regionState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    val regionState: LiveData<FilterFieldsState> = _regionState

    private val _selectButtonState = MutableLiveData<ButtonState>(ButtonState.Gone)
    val selectButtonState: LiveData<ButtonState> = _selectButtonState

    init {
        loadAreaAndCountryFromDataSource()
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

//    fun updateCountryFieldFromArea(areaFilter: AreaFilter?){
//
//    }


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
        addAreaFilterUseCase.execute((areaFilter))
    }

    fun addCountryFilter() {
        countryFilter?.let { addCountryFilterUseCase.execute(it) }
    }

    fun updateSelectButton() {
        if (countryFilter != null || areaFilter != null) {
            setSelectButtonState(ButtonState.Visible)
        } else {
            setSelectButtonState(ButtonState.Gone)
        }
    }

    private fun loadAreaAndCountryFromDataSource() {
        val filterOptions = getFilterOptionsUseCase.execute()
        updateCountryField(filterOptions?.country)
        updateAreaField(filterOptions?.area)
    }

    private fun setCountryState(state: FilterFieldsState) {
        _countryState.value = state
    }

    private fun setRegionState(state: FilterFieldsState) {
        _regionState.value = state
    }

    private fun setSelectButtonState(state: ButtonState) {
        _selectButtonState.value = state
    }
}