package ru.practicum.android.diploma.favorites.ui.viewModel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.FavoritesState
import ru.practicum.android.diploma.favorites.domain.useCase.GetFavoritesUseCase

class FavoritesViewModel(private val getFavoritesUseCase: GetFavoritesUseCase) : ViewModel() {
    private val _stateLiveData = MutableLiveData<FavoritesState>()
    fun stateLiveData(): LiveData<FavoritesState> = _stateLiveData

    val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ -> _stateLiveData.postValue(FavoritesState.Error()) }

    fun getFavorites() {
        viewModelScope.launch(coroutineExceptionHandler) {

            getFavoritesUseCase.execute().collect { listOfVacancies ->
                try{
                    if (listOfVacancies.isEmpty()) {
                        _stateLiveData.postValue(FavoritesState.Empty())
                    } else {
                        _stateLiveData.postValue(FavoritesState.Content(listOfVacancies))
                    }
                } catch(e: SQLiteException){
                    _stateLiveData.postValue(FavoritesState.Error())
                }
            }
        }
    }
}