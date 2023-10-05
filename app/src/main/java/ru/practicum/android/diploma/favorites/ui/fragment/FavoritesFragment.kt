package ru.practicum.android.diploma.favorites.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.VacanciesAdapter
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.domain.FavoritesState
import ru.practicum.android.diploma.favorites.ui.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favouritesViewModel: FavoritesViewModel by viewModel()
    private var favoritesVacancies: List<Vacancy> = listOf()
    private val vacanciesAdapter = VacanciesAdapter {
        clickOnVacancy(it)
    }

    private fun clickOnVacancy(it: Vacancy) {
        /*val action =
        findNavController().navigate(R.id.to_vacancyFragment)*/
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

    }

    override fun onDestroy() {
        super.onDestroy()
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
        binding.favoritesPlaceholderImageView.setImageResource(R.drawable.placeholder_empty_favorites_list)
        binding.favoritesPlaceholderTextView.setText(R.string.the_list_is_empty)
        binding.favouritesRecyclerView.visibility = View.GONE
        binding.favouritesPlaceholderLinearLayout.visibility = View.VISIBLE
    }

    private fun showContent(vacancies: List<Vacancy>) {
        binding.favouritesRecyclerView.visibility = View.VISIBLE
        binding.favouritesPlaceholderLinearLayout.visibility = View.GONE
        vacanciesAdapter.vacancies = vacancies
    }

    private fun showError() {
        binding.favoritesPlaceholderImageView.setImageResource(R.drawable.placeholder_not_found)
        binding.favoritesPlaceholderTextView.setText(R.string.failed_to_get_a_list_of_vacancies)
        binding.favouritesRecyclerView.visibility = View.GONE
        binding.favouritesPlaceholderLinearLayout.visibility = View.VISIBLE
    }
}