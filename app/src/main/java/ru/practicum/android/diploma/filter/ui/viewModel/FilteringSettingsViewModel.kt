package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.model.MyLocation
import ru.practicum.android.diploma.filter.domain.useCase.ClearAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearTempFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetAreaFromGeocoderUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.IsTempFilterOptionsEmptyUseCase
import ru.practicum.android.diploma.filter.domain.useCase.IsTempFilterOptionsExistsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetCountryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetOnlyWithSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetSalaryFilterUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.ClearFieldButtonNavigationState
import ru.practicum.android.diploma.filter.ui.model.DialogState
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.model.LocationState
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.ui.model.SingleLiveEvent
import ru.practicum.android.diploma.vacancy.domain.useCase.OpenAppsSettingsUseCase

class FilteringSettingsViewModel(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val clearFilterOptionsUseCase: ClearFilterOptionsUseCase,
    private val setFilterOptionsUseCase: SetFilterOptionsUseCase,
    private val setCountryFilterUseCase: SetCountryFilterUseCase,
    private val setAreaFilterUseCase: SetAreaFilterUseCase,
    private val setIndustryFilterUseCase: SetIndustryFilterUseCase,
    private val clearAreaFilterUseCase: ClearAreaFilterUseCase,
    private val clearIndustryFilterUseCase: ClearIndustryFilterUseCase,
    private val setSalaryFilterUseCase: SetSalaryFilterUseCase,
    private val clearSalaryFilterUseCase: ClearSalaryFilterUseCase,
    private val setOnlyWithSalaryFilterUseCase: SetOnlyWithSalaryFilterUseCase,
    private val clearTempFilterOptionsUseCase: ClearTempFilterOptionsUseCase,
    private val isTempFilterOptionsEmptyUseCase: IsTempFilterOptionsEmptyUseCase,
    private val isTempFilterOptionsExistsUseCase: IsTempFilterOptionsExistsUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
    private val getAreaFromGeocoderUseCase: GetAreaFromGeocoderUseCase,
    private val openAppsSettingsUseCase: OpenAppsSettingsUseCase

) : ViewModel() {

    private val areaState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val industryState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val salaryState = MutableLiveData<String>(null)
    private val onlyWithSalaryState = MutableLiveData(false)
    private val clearAreaButtonNavigation = MutableLiveData<ClearFieldButtonNavigationState>()
    private val clearIndustryButtonNavigation = MutableLiveData<ClearFieldButtonNavigationState>()
    private val applyButtonState = MutableLiveData<ButtonState>()
    private val resetButtonState = MutableLiveData<ButtonState>()

    private val dialogState = SingleLiveEvent<DialogState>()
    private val locationState = MutableLiveData<LocationState>(LocationState.Empty)


    private var isFiltersSetBefore: Boolean = false

    var filter: Filter? = null

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ -> locationState.value = LocationState.Error }


    fun observeAreaState(): LiveData<FilterFieldsState> = areaState
    fun observeIndustryState(): LiveData<FilterFieldsState> = industryState
    fun observeSalaryState(): LiveData<String> = salaryState
    fun observeOnlyWithSalaryState(): LiveData<Boolean> = onlyWithSalaryState

    fun observeDialogState(): LiveData<DialogState> = dialogState
    fun observeLocationState(): LiveData<LocationState> = locationState


    fun observeClearAreaButtonNavigation(): LiveData<ClearFieldButtonNavigationState> =
        clearAreaButtonNavigation

    fun observeClearIndustryButtonNavigation(): LiveData<ClearFieldButtonNavigationState> =
        clearIndustryButtonNavigation

    fun observeApplyButtonState(): LiveData<ButtonState> = applyButtonState
    fun observeResetButtonState(): LiveData<ButtonState> = resetButtonState

    init {
        filter = getFilterOptionsUseCase.execute()
        isFiltersSetBefore = filter != null
    }

    fun updateStates() {
        filter = getFilterOptionsUseCase.execute()

        val filterUi = filterDomainToFilterUiConverter.mapFilterToFilterUi(filter)

        if (filterUi.areaName.isNotBlank()) {
            areaState.value = FilterFieldsState.Content(
                text = "${filterUi.areaName}, ${filterUi.countryName}"
            )

        } else if (filterUi.areaName.isBlank() && filterUi.countryName.isNotBlank()) {
            areaState.value = FilterFieldsState.Content(
                text = filterUi.countryName
            )
        } else {
            areaState.value = FilterFieldsState.Empty
        }

        if (filterUi.industryName.isNotBlank()) {
            industryState.value = FilterFieldsState.Content(
                text = filterUi.industryName
            )
        } else {
            industryState.value = FilterFieldsState.Empty
        }

        salaryState.value = filterUi.salary
        onlyWithSalaryState.value = filterUi.onlyWithSalary

        updateTempFilters()

        updateButtonsStates()

        locationState.value = LocationState.Empty
    }

    fun updateButtonsStates() {
        if (isFiltersSetBefore) {
            setButtonsStates(
                isTempFiltersNotEmpty = isTempFilterOptionsExistsUseCase.execute()
            )
        } else {
            setButtonsStates(
                isTempFiltersNotEmpty = !isTempFilterOptionsEmptyUseCase.execute()
            )
        }
    }

    fun clearAreaButtonClicked() {
        clearArea()
        updateButtonsStates()
        locationState.value = LocationState.Empty
    }

    fun clearIndustryButtonClicked() {
        clearIndustry()
        updateButtonsStates()
    }

    fun setSalary(salary: Int) {
        setSalaryFilterUseCase.execute(salary)
        salaryState.value = salary.toString()
    }

    fun setOnlyWithSalary(isChecked: Boolean) {
        setOnlyWithSalaryFilterUseCase.execute(isChecked)
    }

    fun clearAll() {
        clearFilterOptionsUseCase.execute()
        clearTempFilterOptionsUseCase.execute()
    }

    fun clearArea() {
        areaState.value = FilterFieldsState.Empty
        clearAreaFilterUseCase.execute()

    }

    fun clearIndustry() {
        industryState.value = FilterFieldsState.Empty
        clearIndustryFilterUseCase.execute()
    }

    fun clearSalary() {
        salaryState.value = BLANK_STRING
        clearSalaryFilterUseCase.execute()
    }

    fun putFilterOptions() {
        if (isTempFilterOptionsEmptyUseCase.execute()) {
            clearFilterOptionsUseCase.execute()
            clearTempFilterOptions()
        } else {
            getFilterOptionsUseCase.execute()
                ?.let { filter -> setFilterOptionsUseCase.execute(filter) }
            clearTempFilterOptions()
        }
    }

    fun clearTempFilterOptions() {
        clearTempFilterOptionsUseCase.execute()
    }

    fun getFilterFromGeocoder(coordinates: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            getAreaFromGeocoderUseCase.execute(coordinates).collect {
                proceedGeocoderResult(it.first, it.second)
            }
        }
    }

    fun getLocation() {
        locationState.value = LocationState.Loading
    }

    private fun proceedGeocoderResult(
        location: MyLocation?, errorStatusDomain: ErrorStatusDomain?
    ) {

        when (errorStatusDomain) {
            ErrorStatusDomain.NO_CONNECTION, ErrorStatusDomain.ERROR_OCCURRED -> {
                locationState.value = LocationState.Error
            }

            null -> {
                val areaFilter = location?.area
                val countryFilter = location?.country
                if (areaFilter != null || countryFilter != null) {
                    setAreaFilterUseCase.execute(areaFilter)
                    setCountryFilterUseCase.execute(countryFilter)
                    updateStates()
                    locationState.value = LocationState.Success
                } else locationState.value = LocationState.Error

            }
        }
    }


    fun locationAccessDenied() {
        dialogState.value = DialogState.ShowDialog
    }

    fun openAppsSettings() {
        openAppsSettingsUseCase.execute()
    }

    private fun setButtonsStates(isTempFiltersNotEmpty: Boolean) {
        if (isTempFiltersNotEmpty) {
            applyButtonState.value = ButtonState.Visible
            resetButtonState.value = ButtonState.Visible
        } else {
            applyButtonState.value = ButtonState.Gone
            resetButtonState.value = ButtonState.Gone
        }
    }

    private fun updateTempFilters() {
        filter?.area?.let {
            setAreaFilterUseCase.execute(it)
        }

        filter?.country?.let {
            setCountryFilterUseCase.execute(it)
        }

        filter?.industry?.let {
            setIndustryFilterUseCase.execute(it)
        }
        filter?.salary?.let {
            setSalaryFilterUseCase.execute(it)
        }

        if (filter?.onlyWithSalary == true) {
            setOnlyWithSalaryFilterUseCase.execute(true)
        }
    }

    companion object {
        const val BLANK_STRING = ""
    }
}