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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.common.util.recycleView.RVAdapter
import ru.practicum.android.diploma.databinding.FragmentFilteringAreaBinding
import ru.practicum.android.diploma.filter.ui.model.AreaNavigationState
import ru.practicum.android.diploma.filter.ui.model.AreasState
import ru.practicum.android.diploma.filter.ui.model.RegionCountryUi
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringAreaViewModel
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringAreaFragment : Fragment() {

    private val args: FilteringAreaFragmentArgs by navArgs()
    private var _binding: FragmentFilteringAreaBinding? = null
    private val binding get() = _binding!!
    private var areasAdapter: RVAdapter? = null

    private var isClickAllowed = true


    private val viewModel by viewModel<FilteringAreaViewModel> {
        parametersOf(args.parentId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringAreaBinding.inflate(inflater, container, false)
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
        binding.filteringRegionEditText.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                viewModel.searchAreaInAreasListUi(text.toString())
            }
        }
        binding.filteringRegionToolbar.setNavigationOnClickListener {
            viewModel.proceedBack()
        }
    }

    private fun renderNavigationState(state: AreaNavigationState) {
        when (state) {
            AreaNavigationState.NavigateEmpty -> findNavController().popBackStack()
            is AreaNavigationState.NavigateWithContent -> {
                val bundle = Bundle()
                bundle.putParcelable(BUNDLE_KEY_FOR_AREA, state.areaFilter)
                setFragmentResult(REQUEST_KEY, bundle)
                findNavController().popBackStack()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderState(state: AreasState) {
        when (state) {
            is AreasState.Error -> showError(state.errorStatus)
            AreasState.Loading -> showLoading()
            is AreasState.Success.Content -> showContent(state.arealList)
        }
    }

    private fun showContent(arealList: List<RegionCountryUi>) {
        emptyScreen()
        areasAdapter?.items = arealList
        binding.filteringRegionRecyclerView.layoutManager?.scrollToPosition(TOP_POSITION_TO_SCROLL)
    }

    private fun showLoading() {
        emptyScreen()
        areasAdapter?.items = emptyList()
        binding.areasScreenProgressBar.isVisible = true
    }

    private fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION, ErrorStatusUi.ERROR_OCCURRED -> {
                emptyScreen()
                areasAdapter?.items = emptyList()
                binding.areasScreenErrorPlaceholder.isVisible = true
            }

            ErrorStatusUi.NOTHING_FOUND -> {
                emptyScreen()
                areasAdapter?.items = emptyList()
                binding.areasScreenNotFoundPlaceholder.isVisible = true
            }
        }
    }

    private fun emptyScreen() {
        binding.areasScreenProgressBar.isVisible = false
        binding.areasScreenErrorPlaceholder.isVisible = false
        binding.areasScreenNotFoundPlaceholder.isVisible = false
    }

    private fun recycleViewInit() {
        areasAdapter = RVAdapter { item ->
            if (isClickDebounce()) {
                viewModel.areaClicked(item.id)
            }
        }

        binding.filteringRegionRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filteringRegionRecyclerView.adapter = areasAdapter
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
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        private const val TOP_POSITION_TO_SCROLL = 0
        const val REQUEST_KEY = "request key"
        const val BUNDLE_KEY_FOR_AREA = "bundle key for area"
    }
}