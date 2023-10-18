package ru.practicum.android.diploma.filter.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        /*        binding.selectedWorkplaceTextInputEditText.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_UP){
                        Toast.makeText(requireContext(), "Нажато \"Место работы\"", Toast.LENGTH_SHORT)
                            .show()
                        binding.selectedWorkplaceTextInputEditText.setText("Some text")
                    }
                    return@setOnTouchListener true
                }*/

        /*binding.selectedWorkplaceTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_to_teamFragment)
                Toast.makeText(requireContext(), "Нажато \"Место работы\"", Toast.LENGTH_SHORT)
                    .show()
            }
        }*/

        binding.workplaceTextInputLayout.setEndIconOnClickListener {
            binding.selectedWorkplaceTextInputEditText.apply {
                if (text.isNullOrBlank()) {
                    binding.workplaceTextInputLayout.endIconDrawable =
                        resources.getDrawable(R.drawable.ic_chevron_right)
                    setText("some text")
                    findNavController().navigate(R.id.action_to_teamFragment)
                } else {
                    binding.workplaceTextInputLayout.endIconDrawable =
                        resources.getDrawable(R.drawable.ic_cross)
                    setText("")
                }
                binding.selectedEnterTheAmountTextInputEditText.clearFocus()
            }


            /*Toast.makeText(
                requireContext(),
                "Нажато на шеврон в \"Место работы\"",
                Toast.LENGTH_SHORT
            )
                .show()*/
        }

        binding.selectedWorkplaceTextInputEditText.doOnTextChanged { input, _, _, _ ->
            binding.workplaceTextInputLayout.apply {
                endIconDrawable = if (!input.isNullOrBlank()) {
                    resources.getDrawable(R.drawable.ic_cross)
                } else {
                    resources.getDrawable(R.drawable.ic_chevron_right)
                }
            }
        }

        /*binding.selectedSectorTextInputEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP){
                Toast.makeText(requireContext(), "Нажато \"Отрасль\"", Toast.LENGTH_SHORT).show()
                binding.selectedSectorTextInputEditText.setText("Any text")
            }
            return@setOnTouchListener true
        }*/

        /*binding.selectedSectorTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
//                findNavController().navigate(R.id.action_filteringSettingsFragment_to_teamFragment)
                findNavController().navigate(R.id.action_to_teamFragment)
                Toast.makeText(requireContext(), "Нажато \"Отрасль\"", Toast.LENGTH_SHORT).show()
            }
        }*/

        binding.sectorTextInputLayout.setEndIconOnClickListener {
            if (binding.selectedSectorTextInputEditText.text.isNullOrBlank()) {
                binding.sectorTextInputLayout.endIconDrawable =
                    resources.getDrawable(R.drawable.ic_chevron_right)
                binding.selectedSectorTextInputEditText.setText("some text")
                findNavController().navigate(R.id.action_to_teamFragment)
            } else {
                binding.sectorTextInputLayout.endIconDrawable =
                    resources.getDrawable(R.drawable.ic_cross)
                binding.selectedSectorTextInputEditText.setText("")
            }
            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
        }

        /*binding.sectorTextInputLayout.setEndIconOnClickListener {
            Toast.makeText(requireContext(), "Нажато на шеврон в \"Отрасль\"", Toast.LENGTH_SHORT)
                .show()
        }*/

        binding.selectedSectorTextInputEditText.doOnTextChanged { input, _, _, _ ->
            binding.sectorTextInputLayout.apply {
                endIconDrawable = if (!input.isNullOrBlank()) {
                    resources.getDrawable(R.drawable.ic_cross)
                } else {
                    resources.getDrawable(R.drawable.ic_chevron_right)
                }
            }
        }


        /*binding.selectedEnterTheAmountTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            binding.selectedEnterTheAmountTextInputEditText.apply {
                if (text.toString() != "" || text.toString() != resources.getString(R.string.enter_the_amount)) {
//                    setTextColor(resources.getColor(R.color.black_universal))
                } else {
//                    setTextColor(resources.getColor(R.color.gray))
                }
            }
        }*/

        binding.filteringSettingsOnlyWithSalaryCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->

            binding.selectedEnterTheAmountTextInputEditText.clearFocus()
//          TODO: надо прятать клаву
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}