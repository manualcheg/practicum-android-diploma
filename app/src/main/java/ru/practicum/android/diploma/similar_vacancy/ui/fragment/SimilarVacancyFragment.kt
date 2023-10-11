package ru.practicum.android.diploma.similar_vacancy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.recycleView.RVAdapter
import ru.practicum.android.diploma.databinding.FragmentSimilarVacanciesBinding
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchError
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.similar_vacancy.ui.viewModel.SimilarVacancyViewModel

class SimilarVacancyFragment : Fragment() {
    private var _binding: FragmentSimilarVacanciesBinding? = null
    private val binding get() = _binding!!

    private val args: SimilarVacancyFragmentArgs by navArgs()

    private val viewModel: SimilarVacancyViewModel by viewModel {
        parametersOf(args.vacancyId)
    }

    private var vacanciesAdapter: RVAdapter? = null

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimilarVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewInit()
        setOnClicksAndActions()

        viewModel.observeState().observe(viewLifecycleOwner) {
            renderSearchState(it)
        }

        viewModel.observeErrorToastState().observe(viewLifecycleOwner) {
            renderErrorState(it)
        }

        isClickAllowed = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.similarVacanciesRecyclerView.adapter = null
        vacanciesAdapter = null
        _binding = null
    }

    private fun recycleViewInit() {
        vacanciesAdapter = RVAdapter { vacancy ->
            if (isClickDebounce()) {
                val direction =
                    SimilarVacancyFragmentDirections.actionSimilarVacancyFragmentToVacancyFragment(
                        vacancy.id
                    )
                findNavController().navigate(direction)
            }
        }

        binding.similarVacanciesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.similarVacanciesRecyclerView.adapter = vacanciesAdapter
    }

    private fun renderSearchState(state: SearchState) {
        when (state) {
            is SearchState.Error -> showError(state.errorStatus)
            is SearchState.Loading.LoadingSearch -> showLoadingSearch()
            is SearchState.Loading.LoadingPages -> showLoadingPages()
            SearchState.Success.Empty -> showEmpty()
            is SearchState.Success.SearchContent -> showContent(state.vacancies)
        }
    }

    private fun renderErrorState(state: SearchError) {
        when (state) {
            SearchError.ERROR_OCCURRED -> {
                showToast(resources.getString(R.string.failed_to_get_a_list_of_vacancies))
                binding.similarVacancyScreenPaginationProgressBar.isVisible = false
            }

            SearchError.NO_CONNECTION -> {
                showToast(resources.getString(R.string.no_internet))
                binding.similarVacancyScreenPaginationProgressBar.isVisible = false
            }
        }
    }


    private fun showContent(vacancies: List<VacancyUi>) {
        emptyScreen()
        vacanciesAdapter?.items = vacancies
    }


    private fun showEmpty() {
        emptyScreen()
        vacanciesAdapter?.items = listOf()
    }

    private fun showLoadingSearch() {
        emptyScreen()
        vacanciesAdapter?.items = listOf()
        binding.similarVacancyScreenPaginationProgressBar.isVisible = true
    }

    private fun showLoadingPages() {
        binding.similarVacancyScreenPaginationProgressBar.isVisible = true
    }

    private fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION -> {
                emptyScreen()
                vacanciesAdapter?.items = listOf()
                binding.similarVacanciesNoInternetPlaceholder.isVisible = true
            }

            ErrorStatusUi.ERROR_OCCURRED -> {
                emptyScreen()
                vacanciesAdapter?.items = listOf()
                binding.similarVacanciesServerErrorPlaceholder.isVisible = true
            }

            ErrorStatusUi.NOTHING_FOUND -> {
                emptyScreen()
                vacanciesAdapter?.items = listOf()
                binding.similarVacanciesNothingFoundPlaceholder.isVisible = true
            }
        }
    }


    private fun setOnClicksAndActions() {

        binding.similarVacanciesToolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.similarVacanciesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos =
                        (binding.similarVacanciesRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacanciesAdapter?.itemCount
                    if (itemsCount != null) {
                        if (pos >= itemsCount - 1) {
                            viewModel.onLastItemReached()
                        }
                    }
                }
            }
        })
    }

    private fun isClickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false

            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun emptyScreen() {
        binding.similarVacanciesServerErrorPlaceholder.isVisible = false
        binding.similarVacanciesNoInternetPlaceholder.isVisible = false
        binding.similarVacanciesScreenFirstLoadingProgressBar.isVisible = false
        binding.similarVacancyScreenPaginationProgressBar.isVisible = false
        binding.similarVacanciesNothingFoundPlaceholder.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}