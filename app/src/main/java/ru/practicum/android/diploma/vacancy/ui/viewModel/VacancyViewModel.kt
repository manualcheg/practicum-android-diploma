package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.vacancy.ui.VacancyState

class VacancyViewModel(private val vacancyId: Int) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    init {
        setState(VacancyState.Load())
    }

    private fun setState(state: VacancyState) {
        _state.postValue(state)
    }
}