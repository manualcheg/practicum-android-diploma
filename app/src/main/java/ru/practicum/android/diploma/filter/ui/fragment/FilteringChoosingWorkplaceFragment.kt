package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.databinding.FragmentFilteringChoosingWorkplaceBinding
import ru.practicum.android.diploma.filter.ui.model.ButtonState
import ru.practicum.android.diploma.filter.ui.model.ChoosingWorkplaceState
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel

class FilteringChoosingWorkplaceFragment : Fragment() {

    private val viewModel by viewModel<FilteringChoosingWorkplaceViewModel>()
    private var _binding: FragmentFilteringChoosingWorkplaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringChoosingWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
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
            val country = bundle.getParcelable(BUNDLE_KEY_FOR_COUNTRY) as CountryFilter?
            val area = bundle.getParcelable(BUNDLE_KEY_FOR_AREA) as AreaFilter?

            if (country != null) {
                viewModel.updateCountryAndAreaField(country, null)
            }
            if (area != null) {
                viewModel.updateCountryAndAreaField(
                    CountryFilter(
                        id = area.countryId,
                        name = area.countryName
                    ), area
                )
            }
        }
    }

    private fun setOnClickListeners() {
        binding.choosingWorkplaceToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.choosingWorkplaceCountryCustomView.setOnClickListener {
            navigateToCountrySelection()
        }

        binding.choosingWorkplaceAreaCustomView.setOnClickListener {
            navigateToAreaSelection()
        }

        binding.choosingWorkplaceSelectButtonTextView.setOnClickListener {
            viewModel.addCountryFilter()
            viewModel.addAreaFilter()
            findNavController().popBackStack()
        }

        binding.choosingWorkplaceCountryCustomView.onButtonClick {
            if (viewModel.countryFilter == null) {
                navigateToCountrySelection()
            } else {
                viewModel.updateCountryAndAreaField(null, null)
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.GONE
            }
        }

        binding.choosingWorkplaceAreaCustomView.onButtonClick {
            if (viewModel.areaFilter == null) {
                navigateToAreaSelection()
            } else {
                viewModel.updateCountryAndAreaField(viewModel.countryFilter, null)
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.VISIBLE
            }
        }

    }

    private fun navigateToCountrySelection() {
        val direction =
            FilteringChoosingWorkplaceFragmentDirections.actionFilteringChoosingWorkplaceFragmentToFilteringCountryFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToAreaSelection() {
        val countryFilter = viewModel.countryFilter
        val countryFilterId = countryFilter?.id?.toString()
        val direction =
            FilteringChoosingWorkplaceFragmentDirections.actionFilteringChoosingWorkplaceFragmentToFilteringRegionFragment(
                countryFilterId
            )
        findNavController().navigate(direction)
    }

    private fun render(state: ChoosingWorkplaceState) {
        when (state) {
            is ChoosingWorkplaceState.EmptyCountryEmptyArea -> {
                renderCountryEmpty()
                renderAreaEmpty()
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.GONE
            }

            is ChoosingWorkplaceState.ContentCountryEmptyArea -> {
                renderCountryContent(state.country)
                renderAreaEmpty()
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.VISIBLE
            }

            is ChoosingWorkplaceState.EmptyCountryContentArea -> {
                renderCountryEmpty()
                renderAreaContent(state.area)
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.VISIBLE
            }

            is ChoosingWorkplaceState.ContentCountryContentArea -> {
                renderCountryContent(state.country)
                renderAreaContent(state.area)
                binding.choosingWorkplaceSelectButtonTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun renderCountryEmpty() {
        binding.choosingWorkplaceCountryCustomView.render(
            ButtonWithSelectedValuesState.Empty(
                getString(
                    R.string.country
                )
            )
        )
    }

    private fun renderCountryContent(country: String) {
        binding.choosingWorkplaceCountryCustomView.render(
            ButtonWithSelectedValuesState.Content(
                country, getString(
                    R.string.country
                )
            )
        )
    }

    private fun renderAreaEmpty() {
        binding.choosingWorkplaceAreaCustomView.render(
            ButtonWithSelectedValuesState.Empty(
                getString(
                    R.string.region
                )
            )
        )
    }

    private fun renderAreaContent(area: String) {
        binding.choosingWorkplaceAreaCustomView.render(
            ButtonWithSelectedValuesState.Content(
                area, getString(
                    R.string.region
                )
            )
        )
    }

    private fun renderSelectButtonVisible() {
        binding.choosingWorkplaceSelectButtonTextView.visibility = View.VISIBLE
    }

    private fun renderSelectButtonGone() {
        binding.choosingWorkplaceSelectButtonTextView.visibility = View.GONE
    }

    companion object {
        const val REQUEST_KEY = "request key"
        const val BUNDLE_KEY_FOR_COUNTRY = "bundle key for country"
        const val BUNDLE_KEY_FOR_AREA = "bundle key for area"
    }
}