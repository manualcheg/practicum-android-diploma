package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.vacancy.domain.useCase.AddVacancyToFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.CallPhoneUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.CheckInFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.DeleteVacancyFromFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.OpenMailUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.ShareVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.ui.model.VacancyState

class VacancyViewModel(
    private val vacancyId: Int,
    private val findVacancyByIdUseCase: FindVacancyByIdUseCase,
    private val addVacancyToFavoritesUseCase: AddVacancyToFavoritesUseCase,
    private val deleteVacancyFromFavoritesUseCase: DeleteVacancyFromFavoritesUseCase,
    private val checkInFavoritesUseCase: CheckInFavoritesUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter,
    private val openMailUseCase: OpenMailUseCase,
    private val shareVacancyByIdUseCase: ShareVacancyByIdUseCase,
    private val callPhoneUseCase: CallPhoneUseCase
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    private var vacancy: Vacancy? = null

    init {
        setState(VacancyState.Load(false))
        checkFavorites()
        findVacancy()
    }

    fun openMail(mailTo: String) {
        openMailUseCase.execute(mailTo)
    }

    fun shareVacancy() {
        shareVacancyByIdUseCase.execute(vacancyId)
    }

    fun dialPhone(phoneNumber: String) {
        callPhoneUseCase.execute(phoneNumber)
    }

    fun findVacancy() {
        viewModelScope.launch {
            val vacancyUI = findVacancyByIdUseCase.findVacancyById(vacancyId)
            if (vacancyUI.vacancy != null) {
                setState(
                    VacancyState.Content(
                        state.value!!.isFavorite,
                        vacancyDomainToVacancyUiConverter.mapVacancyToVacancyUi(
                            vacancyUI.vacancy
                        )
                    )
                )
                vacancy = vacancyUI.vacancy
            } else {
                setState(VacancyState.Error(state.value!!.isFavorite))
            }
        }
    }

    private fun setState(state: VacancyState) {
        _state.value = state
    }

    fun checkFavorites() {
        viewModelScope.launch {
            checkInFavoritesUseCase.execute(vacancyId).collect { isInFavorites ->
                _state.value = _state.value?.apply { isFavorite = isInFavorites }
            }
        }
    }

    fun toggleFavorites() {
        viewModelScope.launch {
            if (state.value!!.isFavorite) {
                vacancy?.let { deleteVacancyFromFavoritesUseCase.execute(it) }
            } else {
                vacancy?.let { addVacancyToFavoritesUseCase.execute(it) }
            }
            _state.value = _state.value?.apply { isFavorite = !isFavorite }
        }

    }
}