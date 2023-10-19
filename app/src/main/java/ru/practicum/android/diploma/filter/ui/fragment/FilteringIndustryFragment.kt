package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringIndustryBinding
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringIndustryViewModel

class FilteringIndustryFragment : Fragment() {

    private var _binding: FragmentFilteringIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilteringIndustryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}