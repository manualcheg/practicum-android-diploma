package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState

class FilteringSettingsViewModel(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter

) : ViewModel() {

    private val areaState =
        MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val industryState =
        MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty)
    private val salaryState = MutableLiveData<String>(null)
    private val onlyWithSalaryState = MutableLiveData(false)

    var filter: Filter? = null

    fun observeAreaState(): LiveData<FilterFieldsState> = areaState
    fun observeIndustryState(): LiveData<FilterFieldsState> = industryState
    fun observeSalaryState(): LiveData<String> = salaryState
    fun observeOnlyWithSalaryState(): LiveData<Boolean> = onlyWithSalaryState

    fun init() {
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
        }

        //МОКОВЫЕ ДАННЫЕ ДЛЯ ПРОВЕРКИ
        areaState.value = FilterFieldsState.Content(
            text = "Москва, Россия"
        )

        industryState.value =
            FilterFieldsState.Content(
                text = "Программирование"
            )

        salaryState.value = "10000"
        onlyWithSalaryState.value = true
    }
}
