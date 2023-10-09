package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.ui.VacancyState

class VacancyViewModel(
    private val vacancyId: Int,
    private val findVacancyByIdUseCase: FindVacancyByIdUseCase
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    init {
        setState(VacancyState.Load())
        findVacancyById(vacancyId)
    }

    private fun findVacancyById(id: Int) {
        viewModelScope.launch {
            val vacancyUI = findVacancyByIdUseCase.findVacancyById(id)
            if (vacancyUI.vacancy != null)
                setState(VacancyState.Content(vacancyUI.vacancy))
            else
                setState(VacancyState.Error())
        }
    }

    private fun setState(state: VacancyState) {
        _state.value = state
    }
}