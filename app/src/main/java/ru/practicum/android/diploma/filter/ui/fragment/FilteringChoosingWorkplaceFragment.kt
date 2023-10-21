package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.databinding.FragmentFilteringChoosingWorkplaceBinding
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.model.SelectButtonState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel

class FilteringChoosingWorkplaceFragment : Fragment() {

    private val viewModel by viewModel<FilteringChoosingWorkplaceViewModel> {
        parametersOf(
            countryFilter, areaFilter
        )
    }

    private var _binding: FragmentFilteringChoosingWorkplaceBinding? = null
    private val binding get() = _binding!!

    private var countryFilter: CountryFilter? = null
    private var areaFilter: AreaFilter? = null

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

        try {
            countryFilter = args.country
            viewModel.updateCountry(countryFilter)
        } catch (_: Throwable) {
        }

        try {
            areaFilter = args.region
            viewModel.updateArea(areaFilter)
        } catch (_: Throwable) {
        }

        viewModel.countryState.observe(viewLifecycleOwner) {
            renderCountryState(it)
        }
        viewModel.regionState.observe(viewLifecycleOwner) {
            renderRegionState(it)
        }
        viewModel.selectButtonState.observe(viewLifecycleOwner) {
            renderSelectButtonState(it)
        }

        setOnClickListeners()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnClickListeners() {

        binding.choosingWorkplaceToolbar.setNavigationOnClickListener {
            findNavController().popBackStack(
                R.id.filteringSettingsFragment,
                false
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack(
                R.id.filteringSettingsFragment,
                false
            )
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
                    .actionFilteringChoosingWorkplaceFragmentToFilteringRegionFragment(countryFilter)
            findNavController().navigate(direction)
        }

        binding.choosingWorkplaceSelectButtonTextView.setOnClickListener {
            countryFilter?.let { it1 -> viewModel.addCountryFilter(it1) }
            areaFilter?.let { it1 -> viewModel.addAreaFilter(it1) }
            findNavController().popBackStack(
                R.id.filteringSettingsFragment,
                false
            )
        }

        binding.choosingWorkplaceCountryCustomView.onButtonClick {
            countryFilter = null
            viewModel.updateCountry(null)
            viewModel.updateSelectButton()
        }

        binding.choosingWorkplaceAreaCustomView.onButtonClick {
            areaFilter = null
            viewModel.updateArea(null)
            viewModel.updateSelectButton()
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
        viewModel.updateSelectButton()
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
        viewModel.updateSelectButton()
    }

    private fun renderSelectButtonState(state: SelectButtonState) {
        when (state) {
            is SelectButtonState.Visible -> {
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.VISIBLE
            }

            is SelectButtonState.Invisible -> {
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.GONE
            }
        }
    }
}