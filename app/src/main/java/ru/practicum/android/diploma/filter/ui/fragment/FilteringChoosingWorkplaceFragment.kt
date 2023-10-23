package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.databinding.FragmentFilteringChoosingWorkplaceBinding
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.model.SelectButtonState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel

class FilteringChoosingWorkplaceFragment : Fragment() {

    private val viewModel by viewModel<FilteringChoosingWorkplaceViewModel>()

    private var _binding: FragmentFilteringChoosingWorkplaceBinding? = null
    private val binding get() = _binding!!


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
        viewModel.selectButtonState.observe(viewLifecycleOwner) {
            renderSelectButtonState(it)
        }

        setOnClickListeners()
        setOnFragmentResultListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnFragmentResultListener() {

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val country = bundle.get(BUNDLE_KEY_FOR_COUNTRY) as CountryFilter?
            val area = bundle.get(BUNDLE_KEY_FOR_AREA) as AreaFilter?
            if (country != null) {
                viewModel.updateCountryField(country)
            }
            if (area != null) {
                viewModel.updateAreaField(area)
            }
        }
    }

    private fun setOnClickListeners() {

        binding.choosingWorkplaceToolbar.setNavigationOnClickListener {
            findNavController().popBackStack(
                R.id.filteringSettingsFragment,
                false
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(
                        R.id.filteringSettingsFragment,
                        false
                    )
                }
            })

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

        binding.choosingWorkplaceSelectButtonTextView.setOnClickListener {
            viewModel.addCountryFilter()
            viewModel.addAreaFilter()
            findNavController().popBackStack(
                R.id.filteringSettingsFragment,
                false
            )
        }

        binding.choosingWorkplaceCountryCustomView.onButtonClick {
            viewModel.updateCountryField(null)
            viewModel.updateSelectButton()
        }

        binding.choosingWorkplaceAreaCustomView.onButtonClick {
            viewModel.updateAreaField(null)
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

    companion object {
        const val REQUEST_KEY = "request key"
        const val BUNDLE_KEY_FOR_COUNTRY = "bundle key for country"
        const val BUNDLE_KEY_FOR_AREA = "bundle key for area"
    }
}