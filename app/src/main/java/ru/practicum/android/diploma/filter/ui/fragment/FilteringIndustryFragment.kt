package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringIndustryBinding
import ru.practicum.android.diploma.filter.ui.RecycleViewIndustryAdapter
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.IndustryNavigationState
import ru.practicum.android.diploma.filter.ui.model.IndustryState
import ru.practicum.android.diploma.filter.ui.model.IndustryUi
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringIndustryViewModel
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringIndustryFragment : Fragment() {

    private var _binding: FragmentFilteringIndustryBinding? = null
    private val binding get() = _binding!!
    private var industriesAdapter: RecycleViewIndustryAdapter? = null

    private var isClickAllowed = true

    private val viewModel by viewModel<FilteringIndustryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewInit()
        initClicks()

        viewModel.observeStateLiveData().observe(viewLifecycleOwner) {
            renderState(it)
        }

        viewModel.observeNavigationStateLiveData().observe(viewLifecycleOwner) {
            renderNavigationState(it)
        }

        viewModel.observeButtonStateLiveData().observe(viewLifecycleOwner) {
            renderButtonState(it)
        }

        binding.filteringSectorEditText.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                viewModel.searchIndustry(text.toString().trim())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun recycleViewInit() {
        industriesAdapter = RecycleViewIndustryAdapter { item ->
            if (isClickDebounce()) {
                viewModel.industryClicked(item.id)
            }
        }

        binding.filteringSectorRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filteringSectorRecyclerView.adapter = industriesAdapter
    }

    private fun initClicks() {
        binding.filteringIndustriesButton.setOnClickListener {
            if (isClickDebounce()) {
                viewModel.chooseButtonClicked()
            }
        }

        binding.filteringSectorToolbar.setNavigationOnClickListener {
            if (isClickDebounce()) {
                viewModel.proceedBack()
            }
        }
    }

    private fun renderState(state: IndustryState) {
        when (state) {
            is IndustryState.Loading -> {
                showLoading()
            }

            is IndustryState.Success -> {
                showContent(state.industryList)
            }

            is IndustryState.Error -> showError(state.errorStatus)
        }
    }

    private fun renderNavigationState(state: IndustryNavigationState) {
        when (state) {
            is IndustryNavigationState.NavigateEmpty -> {
                findNavController().popBackStack()
            }

            is IndustryNavigationState.NavigateWithContent -> {
                viewModel.saveIndustry()
                findNavController().popBackStack()
            }
        }

    }

    private fun renderButtonState(state: ButtonState) {
        when (state) {
            ButtonState.Gone -> binding.filteringIndustriesButton.isVisible = false
            ButtonState.Visible -> binding.filteringIndustriesButton.isVisible = true
        }
    }

    private fun showLoading() {
        emptyScreen()
        industriesAdapter?.items = emptyList()
        binding.industryScreenProgressBar.isVisible = true
    }

    private fun showContent(industries: List<IndustryUi>) {
        emptyScreen()
        industriesAdapter?.items = industries
    }

    private fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION, ErrorStatusUi.ERROR_OCCURRED -> {
                emptyScreen()
                industriesAdapter?.items = emptyList()
                binding.industriesScreenErrorPlaceholder.isVisible = true
            }

            ErrorStatusUi.NOTHING_FOUND -> {
                emptyScreen()
                industriesAdapter?.items = emptyList()
                binding.industriesScreenNotFoundPlaceholder.isVisible = true
            }
        }
    }

    private fun emptyScreen() {
        binding.industryScreenProgressBar.isVisible = false
        binding.industriesScreenNotFoundPlaceholder.isVisible = false
        binding.industriesScreenErrorPlaceholder.isVisible = false
    }

    private fun isClickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false

            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 300L
    }
}