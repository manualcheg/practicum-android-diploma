package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.common.util.recycleView.RVAdapter
import ru.practicum.android.diploma.databinding.FragmentFilteringIndustryBinding
import ru.practicum.android.diploma.filter.ui.model.IndustryNavigationState
import ru.practicum.android.diploma.filter.ui.model.IndustryState
import ru.practicum.android.diploma.filter.ui.model.IndustryUi
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringIndustryViewModel
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringIndustryFragment : Fragment() {

    private var _binding: FragmentFilteringIndustryBinding? = null
    private val binding get() = _binding!!
    private var industriesAdapter: RVAdapter? = null

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

        viewModel.observeStateLiveData().observe(viewLifecycleOwner) {
            renderState(it)
        }

        viewModel.observeNavigationStateLiveData().observe(viewLifecycleOwner) {
            renderNavigationState(it)
        }

        binding.filteringSectorEditText.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                viewModel.searchIndustry(text.toString())

            }
        }
        binding.filteringSectorToolbar.setNavigationOnClickListener {
            viewModel.proceedBack()
        }
    }

    private fun renderNavigationState(state: IndustryNavigationState) {
        when (state) {
            IndustryNavigationState.NavigateEmpty -> findNavController().popBackStack()
            is IndustryNavigationState.NavigateWithContent -> {
                val bundle = Bundle()
                bundle.putParcelable(BUNDLE_KEY_FOR_INDUSTRY, state.industryFilter)
                setFragmentResult(REQUEST_KEY, bundle)
                findNavController().popBackStack()
            }
        }

    }

    private fun renderState(state: IndustryState) {
        when (state) {
            is IndustryState.Loading -> {
                showLoading()
            }

            is IndustryState.Success.Content -> {
                showContent(state.industryList)
            }

            is IndustryState.Error -> showError(state.errorStatus)
        }
    }

    private fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION,
            ErrorStatusUi.ERROR_OCCURRED -> {
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

    private fun showContent(industries: List<IndustryUi>) {
        industriesAdapter?.printItem()
        emptyScreen()
        val list = industries.toList()
        industriesAdapter?.items = list
        binding.filteringSectorRecyclerView.layoutManager?.scrollToPosition(TOP_POSITION_TO_SCROLL)
    }

    private fun showLoading() {
        emptyScreen()
        industriesAdapter?.items = emptyList()
        binding.industryScreenProgressBar.isVisible = true
    }

    private fun emptyScreen() {
        binding.industryScreenProgressBar.isVisible = false
        binding.industriesScreenNotFoundPlaceholder.isVisible = false
        binding.industriesScreenErrorPlaceholder.isVisible = false
        binding.filteringIndustriesButton.isVisible = false
    }

    private fun recycleViewInit() {
        industriesAdapter = RVAdapter { item ->
            if (isClickDebounce()) {
                viewModel.industryClicked(item.id)
            }
        }

        binding.filteringSectorRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filteringSectorRecyclerView.adapter = industriesAdapter
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        private const val TOP_POSITION_TO_SCROLL = 0
        const val REQUEST_KEY = "request key"
        const val BUNDLE_KEY_FOR_INDUSTRY = "bundle key for industry"
    }
}