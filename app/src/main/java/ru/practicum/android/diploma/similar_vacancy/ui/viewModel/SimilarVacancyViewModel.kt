package ru.practicum.android.diploma.similar_vacancy.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.ui.mapper.VacancyDomainToVacancyUiConverter
import ru.practicum.android.diploma.search.ui.model.SearchError
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.search.ui.model.SingleLiveEvent
import ru.practicum.android.diploma.search.ui.viewModel.SearchViewModel
import ru.practicum.android.diploma.similar_vacancy.domain.useCase.SearchSimilarVacanciesByIdUseCase

class SimilarVacancyViewModel(
    val vacancyId: Int,
    private val searchSimilarVacanciesByIdUseCase: SearchSimilarVacanciesByIdUseCase,
    private val vacancyDomainToVacancyUiConverter: VacancyDomainToVacancyUiConverter

) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchState>()
    private val toastErrorStateLiveData = SingleLiveEvent<SearchError>()

    private var foundVacancies = 0
    private var currentPages = 0
    private var nextPage = 0
    private var maxPages = 1
    private var perPage = SearchViewModel.DEFAULT_PER_PAGE

    private var isNextPageLoading = false

    init {

    }


}