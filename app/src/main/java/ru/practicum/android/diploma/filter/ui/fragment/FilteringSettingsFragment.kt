package ru.practicum.android.diploma.filter.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        binding.selectedWorkplaceTextInputEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP){
                Toast.makeText(requireContext(), "Нажато \"Место работы\"", Toast.LENGTH_SHORT)
                    .show()
                binding.selectedWorkplaceTextInputEditText.setText("Some text")
            }
            return@setOnTouchListener true
        }

/*        binding.selectedWorkplaceTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
//            findNavController().navigate(R.id.action_filteringSettingsFragment_to_filteringRegionFragment)
            if (hasFocus) {
                Toast.makeText(requireContext(), "Нажато \"Место работы\"", Toast.LENGTH_SHORT)
                    .show()
            }
        }*/

        binding.selectedSectorTextInputEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP){
                Toast.makeText(requireContext(), "Нажато \"Отрасль\"", Toast.LENGTH_SHORT).show()
                binding.selectedSectorTextInputEditText.setText("Any text")
            }
            return@setOnTouchListener true
        }

/*        binding.selectedSectorTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
//            findNavController().navigate()
            if (hasFocus) {
                Toast.makeText(requireContext(), "Нажато \"Отрасль\"", Toast.LENGTH_SHORT).show()
            }
        }*/


        binding.selectedEnterTheAmountTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            binding.selectedEnterTheAmountTextInputEditText.apply {
                if (hasFocus && (text.toString() != "" || text.toString() != resources.getString(R.string.enter_the_amount))) {
                    setTextColor(resources.getColor(R.color.black_universal))
                } else {
                    setTextColor(resources.getColor(R.color.gray))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}