package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringAreaBinding
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringAreaViewModel

class FilteringAreaFragment : Fragment() {

    private var _binding: FragmentFilteringAreaBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilteringAreaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}