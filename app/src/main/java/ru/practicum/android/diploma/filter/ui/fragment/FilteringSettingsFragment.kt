package ru.practicum.android.diploma.filter.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilteringSettingsBinding
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()

        manageVisibilityOfButtons()
        setOnClicks()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getCross(): Drawable? {
        return ResourcesCompat.getDrawable(resources, R.drawable.ic_cross, null)
    }

    private fun getChevron(): Drawable? {
        return ResourcesCompat.getDrawable(resources, R.drawable.ic_chevron_right, null)
    }

    private fun manageVisibilityOfButtons() {
        binding.apply {
            if (!selectedWorkplaceTextInputEditText.text.isNullOrBlank() ||
                !selectedSectorTextInputEditText.text.isNullOrBlank() ||
                !selectedEnterTheAmountTextInputEditText.text.isNullOrBlank() ||
                filteringSettingsOnlyWithSalaryCheckbox.isChecked
            ) {
                resetButton.visibility = View.VISIBLE
                applyButton.visibility = View.VISIBLE
            } else {
                resetButton.visibility = View.GONE
                applyButton.visibility = View.GONE
            }
        }
    }

    private fun setOnClicks() {
        binding.apply {
            workplaceTextInputLayout.setEndIconOnClickListener {
                selectedWorkplaceTextInputEditText.apply {
                    if (text.isNullOrBlank()) {
                        workplaceTextInputLayout.endIconDrawable = getChevron()
                        setText("some text")
                        findNavController().navigate(R.id.action_to_teamFragment)
                    } else {
                        workplaceTextInputLayout.endIconDrawable = getCross()
                        text?.clear()
                    }
                    selectedEnterTheAmountTextInputEditText.clearFocus()
                }
            }
        }

        binding.apply {
            sectorTextInputLayout.setEndIconOnClickListener {
                if (selectedSectorTextInputEditText.text.isNullOrBlank()) {
                    sectorTextInputLayout.endIconDrawable = getChevron()
                    selectedSectorTextInputEditText.setText("some text")
                    findNavController().navigate(R.id.action_to_teamFragment)
                } else {
                    sectorTextInputLayout.endIconDrawable = getCross()
                    selectedSectorTextInputEditText.text?.clear()
                }
                selectedEnterTheAmountTextInputEditText.clearFocus()
            }
        }

        binding.apply {
            resetButton.setOnClickListener {
                selectedWorkplaceTextInputEditText.text?.clear()
                selectedSectorTextInputEditText.text?.clear()
                selectedEnterTheAmountTextInputEditText.text?.clear()
                filteringSettingsOnlyWithSalaryCheckbox.isChecked = false
                manageVisibilityOfButtons()
            }
        }

        binding.apply{
            filteringSettingsToolbar.setNavigationOnClickListener{
                findNavController().popBackStack()
            }
        }
    }

    private fun setListeners() {
        binding.selectedWorkplaceTextInputEditText.doOnTextChanged { input, _, _, _ ->
            binding.workplaceTextInputLayout.apply {
                endIconDrawable = if (!input.isNullOrBlank()) {
                    getCross()
                } else {
                    getChevron()
                }
                manageVisibilityOfButtons()
            }
        }

        binding.apply {
            selectedSectorTextInputEditText.doOnTextChanged { input, _, _, _ ->
                sectorTextInputLayout.apply {
                    endIconDrawable = if (!input.isNullOrBlank()) {
                        getCross()
                    } else {
                        getChevron()
                    }
                    manageVisibilityOfButtons()
                }
            }
        }

        binding.apply {
            selectedEnterTheAmountTextInputEditText.doOnTextChanged { input, _, _, _ ->
                enterTheAmountTextInputLayout.apply {
                    if (!input.isNullOrBlank() && hasFocus()) {
                        endIconDrawable = getCross()
                    }
                    manageVisibilityOfButtons()
                }
            }
        }

        binding.filteringSettingsOnlyWithSalaryCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
            hideKeyboard()
            manageVisibilityOfButtons()
        }

        binding.selectedEnterTheAmountTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val view: View? = requireActivity().currentFocus
        if (view != null) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }
}