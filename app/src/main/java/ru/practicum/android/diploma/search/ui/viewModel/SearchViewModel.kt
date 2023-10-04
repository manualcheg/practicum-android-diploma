package ru.practicum.android.diploma.search.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.search.domain.useCase.SearchUseCase

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

}