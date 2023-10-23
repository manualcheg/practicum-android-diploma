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
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.util.recycleView.RVAdapter
import ru.practicum.android.diploma.databinding.FragmentFilteringCountryBinding
import ru.practicum.android.diploma.filter.ui.fragment.FilteringChoosingWorkplaceFragment.Companion.BUNDLE_KEY_FOR_COUNTRY
import ru.practicum.android.diploma.filter.ui.fragment.FilteringChoosingWorkplaceFragment.Companion.REQUEST_KEY
import ru.practicum.android.diploma.filter.ui.model.CountryUi
import ru.practicum.android.diploma.filter.ui.model.FilteringCountriesState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringCountryViewModel
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi

class FilteringCountryFragment : Fragment() {

    private var _binding: FragmentFilteringCountryBinding? = null
    private val binding get() = _binding!!

    private var countriesAdapter: RVAdapter? = null

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

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        recyclerViewInit()


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


    private fun recyclerViewInit() {
        countriesAdapter = RVAdapter {
            val item = it as CountryUi
            val direction =
                FilteringCountryFragmentDirections.actionFilteringCountryFragmentToFilteringRegionFragment(
                    CountryFilter(item.id, item.name)
                )
            findNavController().navigate(direction)
        }
        binding.filteringCountryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filteringCountryRecyclerView.adapter = countriesAdapter
    }


    private fun render(state: FilteringCountriesState) {
        when (state) {
            is FilteringCountriesState.Load -> {
                binding.searchScreenPaginationProgressBar.visibility = View.VISIBLE
                countriesAdapter?.items = emptyList()
            }

            is FilteringCountriesState.Content -> {
                binding.searchScreenPaginationProgressBar.visibility = View.GONE
                countriesAdapter?.items = state.countriesList
            }

            is FilteringCountriesState.Error -> {
                binding.searchScreenPaginationProgressBar.visibility = View.GONE
                countriesAdapter?.items = emptyList()
                showError(state.errorStatus)
            }
        }
    }

    private fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION, ErrorStatusUi.ERROR_OCCURRED -> {
                binding.countryScreenErrorPlaceholder.visibility = View.VISIBLE
            }

            ErrorStatusUi.NOTHING_FOUND -> {
                binding.countryScreenNotFoundPlaceholder.visibility = View.VISIBLE
            }
        }
    }
}