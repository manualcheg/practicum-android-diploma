package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringCountryBinding
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringCountryViewModel

class FilteringCountryFragment : Fragment() {

    private var _binding: FragmentFilteringCountryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilteringCountryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}