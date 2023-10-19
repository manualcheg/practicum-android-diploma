package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringChoosingWorkplaceBinding
import ru.practicum.android.diploma.filter.ui.model.FilterFieldsState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel

class FilteringChoosingWorkplaceFragment : Fragment() {

    private val viewModel: FilteringChoosingWorkplaceViewModel by viewModel()

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

        setOnClickListeners()

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFilterOptions()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnClickListeners() {

        binding.choosingWorkplaceToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

//        binding.choosingWorkplaceSelectButtonTextView.setOnClickListener {
//            viewModel.addAreaFilter()
//        }
    }


    private fun renderCountryState(state: FilterFieldsState) {
        when (state) {
            is FilterFieldsState.Empty -> {
                binding.selectedCountryTextInputEditText.text?.clear()
            }

            is FilterFieldsState.Content -> {
                binding.selectedCountryTextInputEditText.setText(state.text)
            }
        }
    }

    private fun renderRegionState(state: FilterFieldsState) {
        when (state) {
            is FilterFieldsState.Empty -> {
                binding.selectedRegionTextInputEditText.text?.clear()
            }

            is FilterFieldsState.Content -> {
                binding.selectedCountryTextInputEditText.setText(state.text)
            }
        }
    }
}