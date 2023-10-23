package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringCountryBinding
import ru.practicum.android.diploma.filter.ui.fragment.FilteringChoosingWorkplaceFragment.Companion.BUNDLE_KEY_FOR_COUNTRY
import ru.practicum.android.diploma.filter.ui.fragment.FilteringChoosingWorkplaceFragment.Companion.REQUEST_KEY
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackClickListeners()


    }

    private fun setOnBackClickListeners() {

        binding.filteringCountryToolbar.setNavigationOnClickListener {
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY_FOR_COUNTRY to null)
            )
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setFragmentResult(
                        REQUEST_KEY,
                        bundleOf(BUNDLE_KEY_FOR_COUNTRY to null)
                    )
                    findNavController().popBackStack()
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}