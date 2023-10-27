package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.filter.domain.useCase.GetChosenAreaUseCase
import ru.practicum.android.diploma.filter.domain.useCase.GetChosenCountryUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetAreaFilterUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetCountryFilterUseCase
import ru.practicum.android.diploma.filter.ui.model.ChoosingWorkplaceState

class FilteringChoosingWorkplaceViewModel(
    private val setAreaFilterUseCase: SetAreaFilterUseCase,
    private val setCountryFilterUseCase: SetCountryFilterUseCase,
    getChosenAreaUseCase: GetChosenAreaUseCase,
    getChosenCountryUseCase: GetChosenCountryUseCase
) : ViewModel() {

    var countryFilter: CountryFilter? = null
    private var areaFilter: AreaFilter? = null

    private val _state = MutableLiveData<ChoosingWorkplaceState>()
    val state: LiveData<ChoosingWorkplaceState> = _state

    init {
        updateCountryAndAreaField(getChosenCountryUseCase.execute(), getChosenAreaUseCase.execute())
    }

    fun updateCountryAndAreaField(countryFilter: CountryFilter?, areaFilter: AreaFilter?) {
        when {
            countryFilter == null && areaFilter == null -> {
                setState(ChoosingWorkplaceState.CountryAndArea.EmptyCountryEmptyArea)
            }

            countryFilter != null && areaFilter == null -> {
                setState(ChoosingWorkplaceState.CountryAndArea.ContentCountryEmptyArea(countryFilter.name))
            }

            countryFilter == null && areaFilter != null -> {
                setState(ChoosingWorkplaceState.CountryAndArea.EmptyCountryContentArea(areaFilter.name))
            }

            countryFilter != null && areaFilter != null -> {
                setState(
                    ChoosingWorkplaceState.CountryAndArea.ContentCountryContentArea(
                        countryFilter.name,
                        areaFilter.name
                    )
                )
            }
        }
        this.countryFilter = countryFilter
        this.areaFilter = areaFilter
    }

    fun addAreaFilter() {
        setAreaFilterUseCase.execute(areaFilter)
    }

    fun addCountryFilter() {
        countryFilter?.let { setCountryFilterUseCase.execute(it) }
    }

    private fun setState(state: ChoosingWorkplaceState) {
        _state.value = state
    }

}













