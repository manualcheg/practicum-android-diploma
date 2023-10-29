package ru.practicum.android.diploma.filter.ui.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.ButtonWithSelectedValues
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.databinding.FragmentFilteringSettingsBinding
import ru.practicum.android.diploma.filter.ui.model.FilterSettingsState
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

        initObservers()

        initListeners()

        viewModel.updateStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initOnClicks() {
        binding.areaCustomView.onButtonClick {
            viewModel.areaButtonClicked()
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
        }

        binding.areaCustomView.setOnClickListener {
            viewModel.onAreaFieldClicked()
        }

        binding.industryCustomView.onButtonClick {
            viewModel.industryButtonClicked()
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
        }

        binding.industryCustomView.setOnClickListener {
            viewModel.onIndustryFieldClicked()
        }

        binding.filteringSettingsToolbar.setNavigationOnClickListener {
            viewModel.backButtonClicked()
        }

        binding.enterTheAmountTextInputLayout.setEndIconOnClickListener {
            viewModel.clearSalaryButtonClicked()
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
            setTextInputLayoutHintColor(
                binding.enterTheAmountTextInputLayout, requireContext(), R.color.gray_white
            )
        }

        binding.filteringSettingsOnlyWithSalaryCheckbox.setOnClickListener {
            viewModel.setOnlyWithSalary(
                binding.filteringSettingsOnlyWithSalaryCheckbox.isChecked
            )
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetButtonClicked()
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
        }

        binding.applyButton.setOnClickListener {
            viewModel.applyButtonClicked()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.backButtonClicked()
        }
    }

    private fun initObservers() {
        viewModel.observeStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: FilterSettingsState) {
        when (state) {
            is FilterSettingsState.Content -> {
                renderButtonWithSelectedValues(
                    state.areaField, binding.areaCustomView, resources.getString(R.string.workplace)
                )
                renderButtonWithSelectedValues(
                    state.industryField,
                    binding.industryCustomView,
                    resources.getString(R.string.industry)
                )

                if (state.isItInitSalaryField) {
                    binding.selectedEnterTheAmountTextInputEditText.setText(state.salaryField)
                }

                if (state.isItInitOnlySalary) {
                    binding.filteringSettingsOnlyWithSalaryCheckbox.isChecked = state.onlyWithSalary
                }

                binding.applyButton.isVisible = state.isApplyButtonVisible

                binding.resetButton.isVisible = state.isResetButtonVisible
            }

            FilterSettingsState.Navigate.NavigateBackWithResult -> {
                val bundle = Bundle()
                bundle.putBoolean(IS_SEARCH_WITH_NEW_FILTER_NEED, true)
                setFragmentResult(IS_SEARCH_WITH_NEW_FILTER_NEED, bundle)
                findNavController().popBackStack()
            }

            FilterSettingsState.Navigate.NavigateBackWithoutResult -> {
                findNavController().popBackStack()
            }

            FilterSettingsState.Navigate.NavigateToChoosingIndustry -> {
                findNavController().navigate(R.id.action_filteringSettingsFragment_to_filteringSectorFragment)
            }

            FilterSettingsState.Navigate.NavigateToChoosingWorkplace -> {
                findNavController().navigate(R.id.action_filteringSettingsFragment_to_filteringChoosingWorkplaceFragment)
            }
        }
    }

    private fun initListeners() {
        binding.selectedEnterTheAmountTextInputEditText.doOnTextChanged { input, _, _, _ ->
            binding.enterTheAmountTextInputLayout.apply {
                if (!input.isNullOrBlank()) {
                    viewModel.setSalaryAmount(input.toString())
                }
                if (input != null && input.isBlank()) {
                    viewModel.clearSalary()
                }
                setHintColor(input.toString())
            }
        }

        binding.filteringSettingsOnlyWithSalaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
            viewModel.setOnlyWithSalary(isChecked)
        }
    }
    private fun renderButtonWithSelectedValues(
        text: String, customView: ButtonWithSelectedValues, hint: String
    ) {
        if (text.isNotBlank()) {
            customView.render(
                ButtonWithSelectedValuesState.Content(
                    text = text, hint = hint
                )
            )
        } else {
            customView.render(
                ButtonWithSelectedValuesState.Empty(hint = hint)
            )
        }
    }

    private fun setHintColor(text: String) {
        binding.selectedEnterTheAmountTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                setTextInputLayoutHintColor(
                    binding.enterTheAmountTextInputLayout, requireContext(), R.color.blue
                )
            } else {
                if (text.isBlank()) {
                    setTextInputLayoutHintColor(
                        binding.enterTheAmountTextInputLayout, requireContext(), R.color.gray_white
                    )
                } else {
                    setTextInputLayoutHintColor(
                        binding.enterTheAmountTextInputLayout,
                        requireContext(),
                        R.color.black_universal
                    )
                }
            }
        }
    }

    private fun setTextInputLayoutHintColor(
        textInputLayout: TextInputLayout, context: Context, @ColorRes colorIdRes: Int
    ) {
        textInputLayout.defaultHintTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, colorIdRes))
    }

    companion object {
        private const val IS_SEARCH_WITH_NEW_FILTER_NEED = "Is search with new filter need"
    }
}