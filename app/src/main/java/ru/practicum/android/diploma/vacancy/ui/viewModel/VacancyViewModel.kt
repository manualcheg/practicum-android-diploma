package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.useCase.AddOrDelVacancyUseCase
import ru.practicum.android.diploma.favorites.domain.useCase.CheckInFavoritesUseCase
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.vacancy.domain.useCase.CallPhoneUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.OpenMailUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.ShareVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.ui.VacancyState

class VacancyViewModel(
    private val vacancyId: Int,
    private val findVacancyByIdUseCase: FindVacancyByIdUseCase,
    private val addOrDelVacancyUseCase: AddOrDelVacancyUseCase,
    private val checkInFavoritesUseCase: CheckInFavoritesUseCase,  
    private val findVacancyByIdUseCase: FindVacancyByIdUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter,
    private val openMailUseCase: OpenMailUseCase,
    private val shareVacancyByIdUseCase: ShareVacancyByIdUseCase,
    private val callPhoneUseCase: CallPhoneUseCase
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    private var _inFavorites = MutableLiveData<Boolean>()
    val inFavorites: LiveData<Boolean> = _inFavorites

    init {
        setState(VacancyState.Load())
    }
    
    fun openMail(mailTo: String) {
        openMailUseCase.execute(mailTo)
    }

    fun shareVacancyById(id: Int) {
        shareVacancyByIdUseCase.execute(id)
    }

    fun dialPhone(phoneNumber: String) {
        callPhoneUseCase.execute(phoneNumber)
    }

    fun findVacancyById(id: Int) {
        viewModelScope.launch {
            val vacancyUI = findVacancyByIdUseCase.findVacancyById(id)
            if (vacancyUI.vacancy != null)
                setState(VacancyState.Content(vacancyDomainToVacancyUiConverter.map(vacancyUI.vacancy)))
            else
                setState(VacancyState.Error())
        }
    }

    private fun setState(state: VacancyState) {
        _state.value = state
    }

    fun checkFavorites(id: Int) {
        viewModelScope.launch {
            checkInFavoritesUseCase.execute(id).collect{
                _inFavorites.postValue(it)
            }
        }
    }

    fun addOrDelFavorites(id: Int) {
        viewModelScope.launch {
            _inFavorites.postValue(addOrDelVacancyUseCase.execute(id))
        }
    }
}