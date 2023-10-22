package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.util.recycleView.RVAdapter
import ru.practicum.android.diploma.databinding.FragmentFilteringCountryBinding
import ru.practicum.android.diploma.filter.ui.model.CountryUi
import ru.practicum.android.diploma.filter.ui.model.FilteringCountriesState
import ru.practicum.android.diploma.filter.ui.viewModel.FilteringCountryViewModel

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

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        recyclerViewInit()
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
                binding.filteringCountryRecyclerView.visibility = View.GONE
            }

            is FilteringCountriesState.Content -> {
                binding.searchScreenPaginationProgressBar.visibility = View.GONE
                binding.filteringCountryRecyclerView.visibility = View.VISIBLE
                countriesAdapter?.items = state.countriesList
            }

            is FilteringCountriesState.Error -> {
                binding.searchScreenPaginationProgressBar.visibility = View.GONE
                binding.filteringCountryRecyclerView.visibility = View.GONE
            }
        }
    }
}