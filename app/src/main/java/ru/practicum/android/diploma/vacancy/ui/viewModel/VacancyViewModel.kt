package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase

class VacancyViewModel(
    private val vacancyId: Int,
    private val findVacancyByIdUseCase: FindVacancyByIdUseCase
) : ViewModel() {

    fun loadVacancyFromDatabase() {
        viewModelScope.launch {
            findVacancyByIdUseCase.findVacancyById(vacancyId)
        }
    }
}