package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.useCase.AddOnlyWithSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.AddSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearTempFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.IsTempFilterOptionsEmptyUseCase
import ru.practicum.android.diploma.filter.domain.useCase.PutFilterOptionsUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.ClearFieldButtonNavigationState
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState

class FilteringSettingsViewModel(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
    private val addSalaryFilterUseCase: AddSalaryFilterUseCase,
    private val addOnlyWithSalaryFilterUseCase: AddOnlyWithSalaryFilterUseCase,
    private val clearFilterOptionsUseCase: ClearFilterOptionsUseCase,
    private val clearAreaFilterUseCase: ClearAreaFilterUseCase,
    private val clearIndustryFilterUseCase: ClearIndustryFilterUseCase,
    private val clearSalaryFilterUseCase: ClearSalaryFilterUseCase,
    private val putFilterOptionsUseCase: PutFilterOptionsUseCase,
    private val clearTempFilterOptionsUseCase: ClearTempFilterOptionsUseCase,
    private val isTempFilterOptionsEmptyUseCase: IsTempFilterOptionsEmptyUseCase
) : ViewModel() {

    private val areaState =
        MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val industryState =
        MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
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
            } else if (filterUi.areaName.isBlank() && filterUi.countryName.isNotBlank()) {
                areaState.value = FilterFieldsState.Content(
                    text = filterUi.countryName
                )
            } else areaState.value = FilterFieldsState.Empty

            if (filterUi.industryName.isNotBlank())
                industryState.value = FilterFieldsState.Content(
                    text = filterUi.industryName
                ) else areaState.value = FilterFieldsState.Empty

            salaryState.value = filterUi.salary
            onlyWithSalaryState.value = filterUi.onlyWithSalary

            setButtonsStates(filter)
        }
    }

    fun updateButtonsStates() {
        setButtonsStates(getFilterOptionsUseCase.execute())
    }

    private fun setButtonsStates(filter: Filter?) {
        if (filter != null) {
            applyButtonState.value = ButtonState.Visible
            resetButtonState.value = ButtonState.Visible
        } else {
            applyButtonState.value = ButtonState.Gone
            resetButtonState.value = ButtonState.Gone
        }
    }

    fun clearAreaButtonClicked() {
        clearArea()
        updateStates()

//        if (areaState.value is FilterFieldsState.Content) {
//            clearAreaButtonNavigation.value = ClearFieldButtonNavigationState.ClearField
//        } else clearAreaButtonNavigation.value = ClearFieldButtonNavigationState.Navigate
    }

    fun clearIndustryButtonClicked() {
        clearIndustry()
        updateStates()

//        if (industryState.value is FilterFieldsState.Content) {
//            clearAreaButtonNavigation.value = ClearFieldButtonNavigationState.ClearField
//        } else clearAreaButtonNavigation.value = ClearFieldButtonNavigationState.Navigate
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
            getFilterOptionsUseCase.execute()?.let { filter ->  putFilterOptionsUseCase.execute(filter) }
        }
    }

    fun clearTempFilterOptions() {
        clearTempFilterOptionsUseCase.execute()
    }

    companion object {
        const val BLANK_STRING = ""
    }
}