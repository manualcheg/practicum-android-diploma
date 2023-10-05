package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.LoadVacancyFromDatabaseUseCase

class VacancyViewModel(
    private val vacancyId: Int,
    private val loadVacancyFromDatabaseUseCase: LoadVacancyFromDatabaseUseCase,
    private val findVacancyUseCase: FindVacancyUseCase
) : ViewModel() {

    fun loadVacancyFromDatabase() {
        viewModelScope.launch {
            loadVacancyFromDatabaseUseCase.getVacancyFromDataBaseById(vacancyId)
        }
    }
}