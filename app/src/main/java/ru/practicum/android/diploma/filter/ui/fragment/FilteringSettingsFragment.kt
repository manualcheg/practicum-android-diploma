package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClicks()

        viewModel.observeWorkPlaceState().observe(viewLifecycleOwner) {
            binding.workplaceCustomView.render(it)
        }

        viewModel.observeIndustryState().observe(viewLifecycleOwner) {
            binding.industryCustomView.render(it)
        }

        viewModel.observeSalaryState().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.selectedEnterTheAmountTextInputEditText.setText(it.toString())
            } else binding.selectedEnterTheAmountTextInputEditText.setText("")
        }

        viewModel.observeOnlyWithSalaryState().observe(viewLifecycleOwner) {
            binding.test.isChecked = it
        }

        viewModel.init()

    }

    private fun initOnClicks() {
        binding.workplaceCustomView.onChevronClick {
            Toast.makeText(requireContext(), "onWorkIconClicked", Toast.LENGTH_SHORT).show()
        }

        binding.industryCustomView.onChevronClick {
            Toast.makeText(requireContext(), "onSectorIconClicked", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}