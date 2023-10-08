package ru.practicum.android.diploma.vacancy.ui.viewModel

import android.util.Log
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
        loadVacancyFromDatabase()
    }

    fun loadVacancyFromDatabase() {
        viewModelScope.launch {
            val vacancy = findVacancyByIdUseCase.findVacancyById(vacancyId)
            Log.e("MyTag", vacancy.first.toString())
            if (vacancy.first != null)
                setState(VacancyState.Content(vacancy.first!!))
            else
                setState(VacancyState.Error())
        }
    }

    private fun setState(state: VacancyState) {
        _state.value = state
    }
}