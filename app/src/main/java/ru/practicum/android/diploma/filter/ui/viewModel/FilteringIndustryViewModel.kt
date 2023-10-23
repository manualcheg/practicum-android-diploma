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
import ru.practicum.android.diploma.filter.ui.mapper.IndustryFilterDomainToIndustryUiConverter
import ru.practicum.android.diploma.filter.ui.model.IndustryNavigationState
import ru.practicum.android.diploma.filter.ui.model.IndustryState
import ru.practicum.android.diploma.filter.ui.model.IndustryUi
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringIndustryViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
    private val industryFilterDomainToIndustryUiConverter: IndustryFilterDomainToIndustryUiConverter
) : ViewModel() {

    private val stateLiveData = MutableLiveData<IndustryState>()
    private val navigationStateLiveData = MutableLiveData<IndustryNavigationState>()

    private val industriesListUi = mutableListOf<IndustryUi>()
    private val foundIndustriesList = mutableListOf<IndustryFilter>()
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ -> setState(IndustryState.Error(ErrorStatusUi.ERROR_OCCURRED)) }

    fun observeStateLiveData(): LiveData<IndustryState> = stateLiveData
    fun observeNavigationStateLiveData(): LiveData<IndustryNavigationState> =
        navigationStateLiveData

    private fun setState(state: IndustryState) {
        stateLiveData.value = state
    }

    init {
        stateLiveData.value = IndustryState.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            getIndustriesUseCase.execute().collect { pair ->
                proceedResult(pair.first, pair.second)
            }
        }
    }

    private fun proceedResult(industries: Industries?, errorStatusDomain: ErrorStatusDomain?) {
        val foundIndustries = industries?.industryList ?: emptyList()
        foundIndustriesList.addAll(foundIndustries)
        val industryList = foundIndustries.map {
            industryFilterDomainToIndustryUiConverter.mapIndustryFilterDomainToIndustryUi(it)
        }
        industriesListUi.addAll(industryList)
        when (errorStatusDomain) {
            ErrorStatusDomain.NO_CONNECTION -> setState(IndustryState.Error(ErrorStatusUi.NO_CONNECTION))
            ErrorStatusDomain.ERROR_OCCURRED -> setState(IndustryState.Error(ErrorStatusUi.ERROR_OCCURRED))
            null -> {
                if (industriesListUi.isEmpty()) {
                    setState(IndustryState.Error(ErrorStatusUi.NOTHING_FOUND))
                } else {
                    setState(IndustryState.Success.Content(industryList))
                }
            }
        }
    }

    fun searchIndustry(query: String) {
        if (query.isBlank()) {
            val newIndustryList = industriesListUi.toMutableList()
            setState(IndustryState.Success.Content(newIndustryList))
        } else {
            val filteredIndustries = industriesListUi.filter {
                it.name.contains(query, ignoreCase = true)
            }.toList()
            if (filteredIndustries.isNotEmpty()) {
                setState(IndustryState.Success.Content(filteredIndustries))
            } else {
                setState(IndustryState.Error(ErrorStatusUi.NOTHING_FOUND))
            }
        }
    }

    fun industryClicked(industryId: Int) {

        val newIndustryList = stateLiveData.value as? IndustryState.Success.Content
        val list = newIndustryList?.industryList?.map { industryUi ->
            industryUi.copy(isSelected = industryUi.id == industryId)
        }
        list?.let { IndustryState.Success.Content(it) }?.let { setState(it) }
    }

    fun proceedBack() {
        navigationStateLiveData.value = IndustryNavigationState.NavigateEmpty
    }
}




