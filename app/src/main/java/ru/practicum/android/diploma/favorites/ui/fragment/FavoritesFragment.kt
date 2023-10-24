package ru.practicum.android.diploma.favorites.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.recycleView.RecycleViewVacancyAdapter
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.domain.FavoritesState
import ru.practicum.android.diploma.favorites.ui.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favouritesViewModel: FavoritesViewModel by viewModel()
    private var vacanciesAdapter: RecycleViewVacancyAdapter? = RecycleViewVacancyAdapter {
        clickOnVacancy(it)
    }
    private var isClickAllowed = true

    private fun clickOnVacancy(vacancy: VacancyUi) {
        if (isClickDebounce()){
            val direction =
                FavoritesFragmentDirections.actionFavoritesFragmentToVacancyFragment(vacancy.id)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritesViewModel.stateLiveData().observe(viewLifecycleOwner) { state ->
            render(state)
        }
        favouritesViewModel.getFavorites()

        binding.favouritesRecyclerView.adapter = vacanciesAdapter
        isClickAllowed = true
    }

    override fun onDestroy() {
        super.onDestroy()

        binding.favouritesRecyclerView.adapter = null
        vacanciesAdapter = null
        _binding = null
    }

    private fun render(state: FavoritesState) {
        when (state) {
            is FavoritesState.Empty -> showEmpty()
            is FavoritesState.Content -> showContent(state.vacancies)
            is FavoritesState.Error -> showError()
        }
    }

    private fun showEmpty() {
        binding.favouritesPlaceholderNotFound.visibility = View.GONE
        binding.favouritesRecyclerView.visibility = View.GONE
        binding.favouritesPlaceholderEmptyList.visibility = View.VISIBLE
    }

    private fun showContent(vacancies: List<VacancyUi>) {
        binding.favouritesRecyclerView.visibility = View.VISIBLE
        vacanciesAdapter?.items = vacancies
        binding.favouritesPlaceholderEmptyList.visibility = View.GONE
        binding.favouritesPlaceholderNotFound.visibility = View.GONE
        binding.favouritesRecyclerView.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.favouritesPlaceholderEmptyList.visibility = View.GONE
        binding.favouritesRecyclerView.visibility = View.GONE
        binding.favouritesPlaceholderNotFound.visibility = View.VISIBLE
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

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}