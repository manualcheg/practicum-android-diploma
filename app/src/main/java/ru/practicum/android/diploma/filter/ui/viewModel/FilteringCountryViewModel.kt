package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.useCase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.ui.mapper.FilterDomainToFilterUiConverter
import ru.practicum.android.diploma.filter.ui.model.CountryUi
import ru.practicum.android.diploma.filter.ui.model.FilteringCountriesState
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringCountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val filterDomainToFilterUiConverter: FilterDomainToFilterUiConverter,
) : ViewModel() {

    private val _state = MutableLiveData<FilteringCountriesState>()
    val state: LiveData<FilteringCountriesState> = _state

    init {
        setState(FilteringCountriesState.Load)
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { pair ->
                when (pair.second) {
                    ErrorStatusDomain.ERROR_OCCURRED -> {
                        setState(FilteringCountriesState.Error(ErrorStatusUi.ERROR_OCCURRED))
                    }

                    ErrorStatusDomain.NO_CONNECTION -> {
                        setState(FilteringCountriesState.Error(ErrorStatusUi.NO_CONNECTION))
                    }

                    null -> {
                        if (pair.first == null) {
                            setState(FilteringCountriesState.Error(ErrorStatusUi.NOTHING_FOUND))
                        } else {
                            val countriesList: List<CountryUi> =
                                filterDomainToFilterUiConverter.mapCountriesFilterToCountriesUi(pair.first!!.countryList)
                            setState(FilteringCountriesState.Content(countriesList))
                        }
                    }
                }
            }
        }
    }

    private fun setState(state: FilteringCountriesState) {
        _state.value = state
    }
}