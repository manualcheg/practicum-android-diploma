package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.GetFilterOptionsUseCase
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState

class FilteringSettingsViewModel(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,

    ) : ViewModel() {

    private val workplaceState =
        MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty(PLACE_OF_WORK))
    private val industryState =
        MutableLiveData<FilterFieldsState>(FilterFieldsState.Empty(INDUSTRY))
    private val salaryState = MutableLiveData<Int?>(null)
    private val onlyWithSalaryState = MutableLiveData(false)

    fun observeWorkPlaceState(): LiveData<FilterFieldsState> = workplaceState
    fun observeIndustryState(): LiveData<FilterFieldsState> = industryState
    fun observeSalaryState(): LiveData<Int?> = salaryState
    fun observeOnlyWithSalaryState(): LiveData<Boolean> = onlyWithSalaryState


    fun init() {
        viewModelScope.launch {
            val filters = getFilterOptionsUseCase.execute()
            if (filters != null) {
                if (filters.area != null) {
                    with(filters.area) {
                        workplaceState.value = FilterFieldsState.Content(
                            text = "$name, $countryName", hint = PLACE_OF_WORK
                        )
                    }
                }
                if (filters.industry != null) {
                    with(filters.industry) {
                        industryState.value = FilterFieldsState.Content(
                            text = name, hint = INDUSTRY
                        )
                    }
                }
                salaryState.value = filters.salary
                onlyWithSalaryState.value = filters.onlyWithSalary
            }
        }

        //МОКОВЫЕ ДАННЫЕ ДЛЯ ПРОВЕРКИ
        workplaceState.value = FilterFieldsState.Content(
            text = "Москва, Россия", hint = PLACE_OF_WORK
        )

        industryState.value =
            FilterFieldsState.Content(
                text = "Программирование", hint = INDUSTRY
            )

        salaryState.value = 10000
        onlyWithSalaryState.value = true
    }


    companion object {
        const val PLACE_OF_WORK = "Место работы"
        const val INDUSTRY = "Отрасль"
    }
}
