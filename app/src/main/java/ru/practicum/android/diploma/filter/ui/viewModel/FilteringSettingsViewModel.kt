package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.useCase.ClearAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearFilterOptionsUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearIndustryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearSalaryFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.ClearTempFilterOptionsUseCase
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
import ru.practicum.android.diploma.filter.ui.model.FilterSettingsState

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
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter
) : ViewModel() {

    private val stateLiveData = MutableLiveData<FilterSettingsState>()

    private var isFiltersSetBefore: Boolean = false

    var filter: Filter? = null

    var salary: String = BLANK_STRING
    private var currentState: FilterSettingsState.Content? = null

    init {
        filter = getFilterOptionsUseCase.execute()
        isFiltersSetBefore = filter != null
    }

    fun observeStateLiveData(): LiveData<FilterSettingsState> = stateLiveData

    fun updateStates() {
        filter = getFilterOptionsUseCase.execute()
        stateLiveData.value = FilterSettingsState.Content()
        currentState = stateLiveData.value as FilterSettingsState.Content

        val filterUi = filterDomainToFilterUiConverter.mapFilterToFilterUi(filter)

        currentState = if (filterUi.areaName.isNotBlank()) {
            currentState?.copy(areaField = "${filterUi.areaName}, ${filterUi.countryName}")

        } else if (filterUi.areaName.isBlank() && filterUi.countryName.isNotBlank()) {
            currentState?.copy(areaField = filterUi.countryName)

        } else {
            currentState?.copy(areaField = BLANK_STRING)
        }

        currentState = if (filterUi.industryName.isNotBlank()) {
            currentState?.copy(industryField = filterUi.industryName)

        } else {
            currentState?.copy(industryField = BLANK_STRING)
        }

        currentState = currentState?.copy(salaryField = filterUi.salary)
        currentState = currentState?.copy(onlyWithSalary = filterUi.onlyWithSalary)


        currentState.let {
            stateLiveData.value = it
        }

        updateTempFilters()
        updateButtonsStates()

        currentState = currentState?.copy(isItInitSalaryField = false)
        currentState = currentState?.copy(isItInitOnlySalary = false)
    }

    private fun updateButtonsStates() {
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

    fun areaButtonClicked() {
        if (currentState?.areaField?.isBlank() == true) {
            stateLiveData.value = FilterSettingsState.Navigate.NavigateToChoosingWorkplace
        } else {
            clearArea()
            updateButtonsStates()
        }
    }

    fun industryButtonClicked() {
        if (currentState?.industryField?.isBlank() == true) {
            stateLiveData.value = FilterSettingsState.Navigate.NavigateToChoosingIndustry
        } else {
            clearIndustry()
            updateButtonsStates()
        }
    }

    fun backButtonClicked() {
        clearTempFilterOptions()
        stateLiveData.value = FilterSettingsState.Navigate.NavigateBackWithoutResult
    }

    fun clearSalaryButtonClicked() {
        clearSalary()
        updateButtonsStates()
    }

    fun resetButtonClicked() {
        clearAll()
        updateStates()
    }

    fun setSalaryAmount(text: String) {
        if (text != salary) {
            salary = text
            setSalaryFilterUseCase.execute(text.toInt())
            currentState = currentState?.copy(salaryField = salary)
            currentState.let {
                stateLiveData.value = it
            }
            updateButtonsStates()
        }
    }

    fun setOnlyWithSalary(isChecked: Boolean) {
        setOnlyWithSalaryFilterUseCase.execute(isChecked)
        updateButtonsStates()
    }

    private fun clearAll() {
        clearFilterOptionsUseCase.execute()
        clearTempFilterOptionsUseCase.execute()
        isFiltersSetBefore = false
    }

    private fun clearArea() {
        currentState = currentState?.copy(areaField = BLANK_STRING)
        currentState.let {
            stateLiveData.value = it
        }
        clearAreaFilterUseCase.execute()

    }

    private fun clearIndustry() {
        currentState = currentState?.copy(industryField = BLANK_STRING)
        currentState.let {
            stateLiveData.value = it
        }
        clearIndustryFilterUseCase.execute()
    }

    fun clearSalary() {
        if (salary != BLANK_STRING) {
            salary = BLANK_STRING
            currentState =
                currentState?.copy(salaryField = BLANK_STRING, isItInitSalaryField = true)
            currentState.let {
                stateLiveData.value = it
            }
            currentState = currentState?.copy(isItInitSalaryField = false)
            clearSalaryFilterUseCase.execute()
            updateButtonsStates()
        }
    }

    fun applyButtonClicked() {
        if (isTempFilterOptionsEmptyUseCase.execute()) {
            clearFilterOptionsUseCase.execute()
            clearTempFilterOptions()
        } else {
            getFilterOptionsUseCase.execute()
                ?.let { filter -> setFilterOptionsUseCase.execute(filter) }
            clearTempFilterOptions()
        }
        stateLiveData.value = FilterSettingsState.Navigate.NavigateBackWithResult
    }

    fun onAreaFieldClicked() {
        stateLiveData.value = FilterSettingsState.Navigate.NavigateToChoosingWorkplace
    }

    fun onIndustryFieldClicked() {
        stateLiveData.value = FilterSettingsState.Navigate.NavigateToChoosingIndustry
    }


    private fun clearTempFilterOptions() {
        clearTempFilterOptionsUseCase.execute()
    }

    private fun setButtonsStates(isTempFiltersNotEmpty: Boolean) {
        if (isTempFiltersNotEmpty) {
            currentState = currentState?.copy(isDataChanged = true)
            currentState.let {
                stateLiveData.value = it
            }
        } else {
            currentState = currentState?.copy(isDataChanged = false)
            currentState.let {
                stateLiveData.value = it
            }
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