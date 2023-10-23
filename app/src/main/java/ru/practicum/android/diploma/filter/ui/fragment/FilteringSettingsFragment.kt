package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.ButtonWithSelectedValues
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.databinding.FragmentFilteringSettingsBinding
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringSettingsViewModel

class FilteringSettingsFragment : Fragment() {

    private var _binding: FragmentFilteringSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilteringSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClicks()

        viewModel.observeAreaState().observe(viewLifecycleOwner) {
            renderButtonWithSelectedValues(
                it,
                binding.areaCustomView,
                resources.getString(R.string.workplace)
            )
        }

        viewModel.observeIndustryState().observe(viewLifecycleOwner) {
            renderButtonWithSelectedValues(
                it,
                binding.industryCustomView,
                resources.getString(R.string.industry)
            )
        }

        viewModel.observeSalaryState().observe(viewLifecycleOwner) {
            binding.selectedEnterTheAmountTextInputEditText.setText(it)
        }

        viewModel.observeOnlyWithSalaryState().observe(viewLifecycleOwner) {
            binding.test.isChecked = it
        }

        viewModel.init()

    }


    private fun renderButtonWithSelectedValues(
        state: FilterFieldsState,
        customView: ButtonWithSelectedValues,
        hint: String
    ) {
        when (state) {
            is FilterFieldsState.Content -> customView.render(
                ButtonWithSelectedValuesState.Content(
                    text = state.text,
                    hint = hint
                )
            )

            FilterFieldsState.Empty -> customView.render(
                ButtonWithSelectedValuesState.Empty(
                    hint = hint
                )
            )
        }
    }

    private fun initOnClicks() {
        binding.areaCustomView.onButtonClick {
            val direction =
                FilteringSettingsFragmentDirections.actionFilteringSettingsFragmentToFilteringRegionFragment(
                    null
                )
            findNavController().navigate(direction)
        }

        binding.industryCustomView.onButtonClick {
            val direction =
                FilteringSettingsFragmentDirections.actionFilteringSettingsFragmentToFilteringSectorFragment()
            findNavController().navigate(direction)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}