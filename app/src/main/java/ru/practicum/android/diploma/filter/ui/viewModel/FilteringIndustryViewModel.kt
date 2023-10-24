package ru.practicum.android.diploma.filter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.filter.domain.useCase.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.useCase.SetIndustryFilterUseCase
import ru.practicum.android.diploma.filter.ui.mapper.IndustryFilterDomainToIndustryUiConverter
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.IndustryNavigationState
import ru.practicum.android.diploma.filter.ui.model.IndustryState
import ru.practicum.android.diploma.filter.ui.model.IndustryUi
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringIndustryViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
    private val industryFilterDomainToIndustryUiConverter: IndustryFilterDomainToIndustryUiConverter,
    private val setIndustryFilterUseCase: SetIndustryFilterUseCase
) : ViewModel() {

    private val stateLiveData = MutableLiveData<IndustryState>()
    private val navigationStateLiveData = MutableLiveData<IndustryNavigationState>()
    private val buttonStateLiveData = MutableLiveData<ButtonState>(ButtonState.Gone)

    private val industriesListUi = mutableListOf<IndustryUi>()

    private var industry: IndustryFilter? = null

    private val foundIndustriesList = mutableListOf<IndustryFilter>()
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ -> setState(IndustryState.Error(ErrorStatusUi.ERROR_OCCURRED)) }

    init {
        stateLiveData.value = IndustryState.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            getIndustriesUseCase.execute().collect { pair ->
                proceedResult(pair.first, pair.second)
            }
        }
    }

    fun observeStateLiveData(): LiveData<IndustryState> = stateLiveData

    fun observeNavigationStateLiveData(): LiveData<IndustryNavigationState> =
        navigationStateLiveData

    fun observeButtonStateLiveData(): LiveData<ButtonState> = buttonStateLiveData

    fun searchIndustry(query: String) {
        if (query.isBlank()) {
            setState(IndustryState.Success.Content(industriesListUi))
        } else {
            val filteredIndustries = industriesListUi.filter {
                it.name.contains(query, ignoreCase = true)
            }
            if (filteredIndustries.isNotEmpty()) {
                setState(IndustryState.Success.Content(filteredIndustries))
                setButtonStateVisibilityWithCondition()
            } else {
                setState(IndustryState.Error(ErrorStatusUi.NOTHING_FOUND))
                setButtonState(ButtonState.Gone)
            }
        }
    }

    fun industryClicked(industryId: Int) {
        val currentList = (stateLiveData.value as? IndustryState.Success.Content)?.industryList
        val list = currentList?.map { industryUi ->
            industryUi.copy(isSelected = industryUi.id == industryId)
        }
        if (list != null) {
            setState(IndustryState.Success.Content(list))
        }

        industry = foundIndustriesList.find { it.id == industryId }
        setButtonStateVisibilityWithCondition()
        industriesListUi.forEach {
            it.isSelected = it.id == industryId
        }
    }

    fun chooseButtonClicked() {
        if (industry != null) {
            setNavigationState(IndustryNavigationState.NavigateWithContent(industry))
        } else {
            navigationStateLiveData.value = IndustryNavigationState.NavigateEmpty
        }
    }

    fun saveIndustry() {
        industry?.let { setIndustryFilterUseCase.execute(it) }
    }

    fun proceedBack() {
        navigationStateLiveData.value = IndustryNavigationState.NavigateEmpty
    }

    private fun proceedResult(industries: Industries?, errorStatusDomain: ErrorStatusDomain?) {
        updateIndustriesLists(industries)
        when (errorStatusDomain) {
            ErrorStatusDomain.NO_CONNECTION -> {
                setState(IndustryState.Error(ErrorStatusUi.NO_CONNECTION))
                setButtonState(ButtonState.Gone)
            }

            ErrorStatusDomain.ERROR_OCCURRED -> {
                setState(IndustryState.Error(ErrorStatusUi.ERROR_OCCURRED))
                setButtonState(ButtonState.Gone)
            }

            null -> {
                if (industriesListUi.isEmpty()) {
                    setState(IndustryState.Error(ErrorStatusUi.NOTHING_FOUND))
                    setButtonState(ButtonState.Gone)
                } else {
                    setState(IndustryState.Success.Content(industriesListUi))
                    setButtonStateVisibilityWithCondition()
                }
            }
        }
    }

    private fun updateIndustriesLists(industries: Industries?) {
        val foundIndustries = industries?.industryList ?: emptyList()
        foundIndustriesList.addAll(foundIndustries)
        val industryList = foundIndustries.map {
            industryFilterDomainToIndustryUiConverter.mapIndustryFilterDomainToIndustryUi(it)
        }
        industriesListUi.addAll(industryList)
    }

    private fun setState(state: IndustryState) {
        stateLiveData.value = state
    }

    private fun setButtonState(state: ButtonState) {
        buttonStateLiveData.value = state
    }

    private fun setNavigationState(state: IndustryNavigationState) {
        navigationStateLiveData.value = state
    }

    private fun setButtonStateVisibilityWithCondition() {
        if (industry != null) {
            setButtonState(ButtonState.Visible)
        } else setButtonState(ButtonState.Gone)
    }
}