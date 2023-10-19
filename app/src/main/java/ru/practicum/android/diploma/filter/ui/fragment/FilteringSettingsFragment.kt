package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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

    private var area = ""
    private var industry = ""
    private var salary = ""
    private var isOnlyWithSalary = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClicks()

        workWithObserves()

        workWithListeners()

//        viewModel.init()
        viewModel.initOnce()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
            if (viewModel.observeAreaState().value is FilterFieldsState.Content) {
                viewModel.clearArea()
                viewModel.init()
            } else {
                findNavController().navigate(R.id.action_filteringSettingsFragment_to_filteringChoosingWorkplaceFragment)
            }
            Toast.makeText(context, "Нажато", Toast.LENGTH_SHORT).show()
        }

        binding.industryCustomView.onButtonClick {
            if (viewModel.observeIndustryState().value is FilterFieldsState.Content) {
                viewModel.clearIndustry()
                viewModel.init()
            } else {
                findNavController().navigate(R.id.action_filteringSettingsFragment_to_filteringSectorFragment)
            }
            Toast.makeText(context, "Нажато", Toast.LENGTH_SHORT).show()
        }

        binding.filteringSettingsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.enterTheAmountTextInputLayout.setEndIconOnClickListener {
            if (!binding.selectedEnterTheAmountTextInputEditText.text.isNullOrBlank()) {
                binding.selectedEnterTheAmountTextInputEditText.text!!.clear()
                viewModel.clearSalary()
                viewModel.init()
                binding.selectedEnterTheAmountTextInputEditText.clearFocus()
            }
        }

        binding.resetButton.setOnClickListener {
            viewModel.clearAll()
            viewModel.init()
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
        }

        binding.applyButton.setOnClickListener {
            viewModel.putFilterOptions()
            findNavController().popBackStack()
        }
    }

    private fun workWithObserves() {
        viewModel.observeAreaState().observe(viewLifecycleOwner) {
            renderButtonWithSelectedValues(
                it,
                binding.areaCustomView,
                resources.getString(R.string.workplace)
            )
            if (it is FilterFieldsState.Content) {
                area = it.text
            }
            manageVisibilityOfButtons()
        }

        viewModel.observeIndustryState().observe(viewLifecycleOwner) {
            renderButtonWithSelectedValues(
                it,
                binding.industryCustomView,
                resources.getString(R.string.industry)
            )
            if (it is FilterFieldsState.Content) {
                industry = it.text
            }
            manageVisibilityOfButtons()
        }

        viewModel.observeSalaryState().observe(viewLifecycleOwner) {
            if (it != salary) {
                binding.selectedEnterTheAmountTextInputEditText.setText(it)
                salary = it
                manageVisibilityOfButtons()
            }
        }

        viewModel.observeOnlyWithSalaryState().observe(viewLifecycleOwner) {
            binding.filteringSettingsOnlyWithSalaryCheckbox.isChecked = it
            isOnlyWithSalary = it
            manageVisibilityOfButtons()
        }
    }

    private fun workWithListeners() {
        binding.selectedEnterTheAmountTextInputEditText.doOnTextChanged { input, _, _, _ ->
            binding.enterTheAmountTextInputLayout.apply {
                if (!input.isNullOrBlank()) {
                    viewModel.putSalary(input.toString().toInt())
                }
            }
        }

        binding.filteringSettingsOnlyWithSalaryCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
            viewModel.putOnlyWithSalary(isChecked)
            viewModel.init()
        }
    }

    private fun manageVisibilityOfButtons() {
        binding.apply {
            if (area != "" || industry != "" || salary != "" || isOnlyWithSalary) {
                resetButton.visibility = View.VISIBLE
                applyButton.visibility = View.VISIBLE
            } else {
                resetButton.visibility = View.GONE
                applyButton.visibility = View.GONE
            }
        }
    }
}