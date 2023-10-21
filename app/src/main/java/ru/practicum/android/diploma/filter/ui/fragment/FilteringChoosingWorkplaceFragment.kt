package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.databinding.FragmentFilteringChoosingWorkplaceBinding
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel

class FilteringChoosingWorkplaceFragment : Fragment() {

    private val viewModel by viewModel<FilteringChoosingWorkplaceViewModel>()

    private var _binding: FragmentFilteringChoosingWorkplaceBinding? = null
    private val binding get() = _binding!!

    private val args: FilteringChoosingWorkplaceFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringChoosingWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryState.observe(viewLifecycleOwner) {
            renderCountryState(it)
        }
        viewModel.regionState.observe(viewLifecycleOwner) {
            renderRegionState(it)
        }

        try {
            viewModel.countryFilterFromSafeArgs = args.country
        } catch (_: Throwable) {
            viewModel.countryFilterFromSafeArgs = null
        }

        try {
            viewModel.areaFilter = args.region
        } catch (_: Throwable) {
            viewModel.areaFilter = null
        }
        viewModel.updateFromSafeArgs()

        setOnClickListeners()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnClickListeners() {

        binding.choosingWorkplaceToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.choosingWorkplaceCountryCustomView.setOnClickListener {
            val direction =
                FilteringChoosingWorkplaceFragmentDirections
                    .actionFilteringChoosingWorkplaceFragmentToFilteringCountryFragment()
            findNavController().navigate(direction)
        }

        binding.choosingWorkplaceAreaCustomView.setOnClickListener {
            val direction =
                FilteringChoosingWorkplaceFragmentDirections
                    .actionFilteringChoosingWorkplaceFragmentToFilteringRegionFragment(viewModel.countryFilter)
            findNavController().navigate(direction)
        }

        binding.choosingWorkplaceCountryCustomView.onButtonClick {
            viewModel.countryFilter = null
            //viewModel.loadFilterOptions()
            binding.choosingWorkplaceCountryCustomView.render(
                ButtonWithSelectedValuesState.Empty(
                    getString(R.string.country)
                )
            )
        }

        binding.choosingWorkplaceAreaCustomView.onButtonClick {
            viewModel.areaFilter = null
            viewModel.loadFilterOptions()
        }

        binding.choosingWorkplaceSelectButtonTextView.setOnClickListener {
            viewModel.countryFilter?.let { it1 -> viewModel.addCountryFilter(it1) }
            viewModel.areaFilter?.let { it1 -> viewModel.addAreaFilter(it1) }
            findNavController().popBackStack()
        }
    }


    private fun renderCountryState(state: FilterFieldsState) {
        when (state) {
            is FilterFieldsState.Empty -> {
                binding.choosingWorkplaceCountryCustomView.render(
                    ButtonWithSelectedValuesState.Empty(
                        getString(
                            R.string.country
                        )
                    )
                )
            }

            is FilterFieldsState.Content -> {
                binding.choosingWorkplaceCountryCustomView.render(
                    ButtonWithSelectedValuesState.Content(
                        state.text, getString(
                            R.string.country
                        )
                    )
                )
            }
        }
    }

    private fun renderRegionState(state: FilterFieldsState) {
        when (state) {
            is FilterFieldsState.Empty -> {
                binding.choosingWorkplaceAreaCustomView.render(
                    ButtonWithSelectedValuesState.Empty(
                        getString(
                            R.string.region
                        )
                    )
                )
            }

            is FilterFieldsState.Content -> {
                binding.choosingWorkplaceAreaCustomView.render(
                    ButtonWithSelectedValuesState.Content(
                        state.text, getString(
                            R.string.region
                        )
                    )
                )
            }
        }
    }
}