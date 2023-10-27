package ru.practicum.android.diploma.filter.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.useCase.GetChosenAreaUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetChosenCountryUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetCountryFilterUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.ChoosingWorkplaceState

class FilteringChoosingWorkplaceViewModel(
    private val setAreaFilterUseCase: SetAreaFilterUseCase,
    private val setCountryFilterUseCase: SetCountryFilterUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
    getChosenAreaUseCase: GetChosenAreaUseCase,
    getChosenCountryUseCase: GetChosenCountryUseCase
) : ViewModel() {

    var countryFilter: CountryFilter? = null
    private var areaFilter: AreaFilter? = null

    private val _state = MutableLiveData<ChoosingWorkplaceState>()
    val state: LiveData<ChoosingWorkplaceState> = _state

//    private val _countryState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
//    val countryState: LiveData<FilterFieldsState> = _countryState
//
//    private val _regionState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
//    val regionState: LiveData<FilterFieldsState> = _regionState
//
//    private val _selectButtonState = MutableLiveData<ButtonState>(ButtonState.Gone)
//    val selectButtonState: LiveData<ButtonState> = _selectButtonState

    init {
        updateCountryField(getChosenCountryUseCase.execute())
        updateAreaField(getChosenAreaUseCase.execute())
    }

    fun updateCountryField(countryFilter: CountryFilter?) {
        if (countryFilter != null) {
            setState(
                ChoosingWorkplaceState.Country.Content(
                    filterDomainToFilterUiConverter.mapCountryFilterToCountryUi(countryFilter).name
                )
            )
        } else {
            setState(ChoosingWorkplaceState.Country.Empty)
        }
        this.countryFilter = countryFilter
    }

    fun updateAreaField(areaFilter: AreaFilter?) {
        if (areaFilter != null) {
            setState(
                ChoosingWorkplaceState.Area.Content(
                    filterDomainToFilterUiConverter.mapAreaFilterToRegionIndustryUi(areaFilter).name
                )
            )
        } else {
            setState(ChoosingWorkplaceState.Area.Empty)
        }
        this.areaFilter = areaFilter
    }

    fun addAreaFilter() {
        setAreaFilterUseCase.execute(areaFilter)
    }

    fun addCountryFilter() {
        countryFilter?.let { setCountryFilterUseCase.execute(it) }
    }

    fun updateSelectButton() {
        if (countryFilter != null || areaFilter != null) {
            setState(ChoosingWorkplaceState.SelectButton.Visible)
        } else {
            setState(ChoosingWorkplaceState.SelectButton.Gone)
        }
    }

    private fun setState(state: ChoosingWorkplaceState) {
        _state.value = state
        Log.e("MyTag", "ViewModelState: ${this.state.value}")
    }

//    private fun setCountryState(state: FilterFieldsState) {
//        _countryState.value = state
//    }
//
//    private fun setRegionState(state: FilterFieldsState) {
//        _regionState.value = state
//    }
//
//    private fun setSelectButtonState(state: ButtonState) {
//        _selectButtonState.value = state
//    }
}













