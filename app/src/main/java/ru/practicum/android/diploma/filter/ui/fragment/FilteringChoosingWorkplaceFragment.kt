package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringChoosingWorkplaceBinding
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringChoosingWorkplaceViewModel

class FilteringChoosingWorkplaceFragment : Fragment() {

    private var _binding: FragmentFilteringChoosingWorkplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilteringChoosingWorkplaceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringChoosingWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}