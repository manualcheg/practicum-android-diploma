package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.useCase.AddAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddCountryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddOnlyWithSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearTempFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.IsTempFilterOptionsEmptyUseCase
import ru.practicum.android.diploma.filter.domain.useCase.IsTempFilterOptionsExistsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.ClearFieldButtonNavigationState
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState

class FilteringSettingsViewModel(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val clearFilterOptionsUseCase: ClearFilterOptionsUseCase,
    private val putFilterOptionsUseCase: PutFilterOptionsUseCase,
    private val addCountryFilterUseCase: AddCountryFilterUseCase,
    private val addAreaFilterUseCase: AddAreaFilterUseCase,
    private val addIndustryFilterUseCase: AddIndustryFilterUseCase,
    private val clearAreaFilterUseCase: ClearAreaFilterUseCase,
    private val clearIndustryFilterUseCase: ClearIndustryFilterUseCase,
    private val addSalaryFilterUseCase: AddSalaryFilterUseCase,
    private val clearSalaryFilterUseCase: ClearSalaryFilterUseCase,
    private val addOnlyWithSalaryFilterUseCase: AddOnlyWithSalaryFilterUseCase,
    private val clearTempFilterOptionsUseCase: ClearTempFilterOptionsUseCase,
    private val isTempFilterOptionsEmptyUseCase: IsTempFilterOptionsEmptyUseCase,
    private val isTempFilterOptionsExistsUseCase: IsTempFilterOptionsExistsUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter
) : ViewModel() {

    private val areaState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val industryState = MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val salaryState = MutableLiveData<String>(null)
    private val onlyWithSalaryState = MutableLiveData(false)
    private val clearAreaButtonNavigation = MutableLiveData<ClearFieldButtonNavigationState>()
    private val clearIndustryButtonNavigation = MutableLiveData<ClearFieldButtonNavigationState>()
    private val applyButtonState = MutableLiveData<ButtonState>()
    private val resetButtonState = MutableLiveData<ButtonState>()

    var filter: Filter? = null

    fun observeAreaState(): LiveData<FilterFieldsState> = areaState
    fun observeIndustryState(): LiveData<FilterFieldsState> = industryState
    fun observeSalaryState(): LiveData<String> = salaryState
    fun observeOnlyWithSalaryState(): LiveData<Boolean> = onlyWithSalaryState

    fun observeClearAreaButtonNavigation(): LiveData<ClearFieldButtonNavigationState> =
        clearAreaButtonNavigation

    fun observeClearIndustryButtonNavigation(): LiveData<ClearFieldButtonNavigationState> =
        clearIndustryButtonNavigation

    fun observeApplyButtonState(): LiveData<ButtonState> = applyButtonState
    fun observeResetButtonState(): LiveData<ButtonState> = resetButtonState

    fun updateStates() {
        viewModelScope.launch {

            filter = getFilterOptionsUseCase.execute()


            val filterUi = filterDomainToFilterUiConverter.mapFilterToFilterUi(filter)

            if (filterUi.areaName.isNotBlank()) {
                areaState.value = FilterFieldsState.Content(
                    text = "${filterUi.areaName}, ${filterUi.countryName}"
                )
                filter?.area?.let { addAreaFilterUseCase.execute(it) }
            } else if (filterUi.areaName.isBlank() && filterUi.countryName.isNotBlank()) {
                areaState.value = FilterFieldsState.Content(
                    text = filterUi.countryName
                )
                filter?.country?.let { addCountryFilterUseCase.execute(it) }
            } else {
                areaState.value = FilterFieldsState.Empty
            }

            if (filterUi.industryName.isNotBlank()) {
                industryState.value = FilterFieldsState.Content(
                    text = filterUi.industryName
                )
                filter?.industry?.let { addIndustryFilterUseCase.execute(it) }
            } else {
                industryState.value = FilterFieldsState.Empty
            }

            salaryState.value = filterUi.salary
            filter?.salary?.let { addSalaryFilterUseCase.execute(it) }

            onlyWithSalaryState.value = filterUi.onlyWithSalary
            if (filter?.onlyWithSalary == true) {
                addOnlyWithSalaryFilterUseCase.execute(true)
            }
        }
        updateButtonsStates()
    }


    fun updateButtonsStates() {
        setButtonsStates(
            isTempFiltersNotEmpty = isTempFilterOptionsExistsUseCase.execute()
        )
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

    fun clearAreaButtonClicked() {
        clearArea()
        updateButtonsStates()
    }

    fun clearIndustryButtonClicked() {
        clearIndustry()
        updateButtonsStates()
    }

    fun setSalary(salary: Int) {
        addSalaryFilterUseCase.execute(salary)
        salaryState.value = salary.toString()
    }

    fun setOnlyWithSalary(isChecked: Boolean) {
        addOnlyWithSalaryFilterUseCase.execute(isChecked)
    }

    fun clearAll() {
        clearFilterOptionsUseCase.execute()
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
        } else {
            getFilterOptionsUseCase.execute()
                ?.let { filter -> putFilterOptionsUseCase.execute(filter) }
            clearTempFilterOptions()
        }
    }

    fun clearTempFilterOptions() {
        clearTempFilterOptionsUseCase.execute()
    }

    companion object {
        const val BLANK_STRING = ""
    }
}