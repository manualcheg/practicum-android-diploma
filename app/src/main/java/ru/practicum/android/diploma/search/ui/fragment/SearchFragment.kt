package ru.practicum.android.diploma.search.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.VacanciesAdapter
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.search.ui.model.TextWatcherJustOnTextChanged
import ru.practicum.android.diploma.search.ui.viewModel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private var textWatcher: TextWatcher? = null
    private var vacanciesAdapter: VacanciesAdapter? = null

    private var inputSearchText: String = DEFAULT_TEXT
    private var isClickAllowed = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewInit()
        setOnClicksAndActions()
        setOnTextWatchersTextChangeListeners()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        isClickAllowed = true
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { binding.searchScreenEditText.removeTextChangedListener(it) }
        binding.searchScreenRecyclerView.adapter = null
        vacanciesAdapter = null
        _binding = null
    }

    private fun recycleViewInit() {
        vacanciesAdapter = VacanciesAdapter { vacancy ->
            if (isClickDebounce()) {
                val direction =
                    SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancy.id)
                findNavController().navigate(direction)
            }
        }

        binding.searchScreenRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchScreenRecyclerView.adapter = vacanciesAdapter
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Error -> showError(state.errorStatus)
            SearchState.Loading -> showLoading()
            SearchState.Success.Empty -> showEmpty()
            is SearchState.Success.SearchContent -> showContent(state.vacancies)
        }
    }

    private fun showContent(vacancies: List<Vacancy>) {
        emptyScreen()
        vacanciesAdapter?.vacancies = vacancies
        binding.searchScreenRecyclerView.isVisible = true
        binding.counterVacanciesTextView.text = resources.getQuantityString(
            R.plurals.vacancy_plural, vacancies.size, vacancies.size
        )
        binding.counterVacanciesTextView.isVisible = true
    }


    private fun showEmpty() {
        emptyScreen()
        binding.placeholderSearchVacanciesImageView.isVisible = true
    }

    private fun showLoading() {
        emptyScreen()
        binding.searchScreenFirstLoadingProgressBar.isVisible = true
    }

    private fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION -> {
                emptyScreen()
                binding.searchScreenNoInternetPlaceholder.isVisible = true
            }

            ErrorStatusUi.ERROR_OCCURRED -> {
                emptyScreen()
                binding.searchScreenServerErrorPlaceholder.isVisible = true
            }

            ErrorStatusUi.NOTHING_FOUND -> {
                emptyScreen()
                binding.searchScreenNothingFoundPlaceholder.isVisible = true
                binding.counterVacanciesTextView.text =
                    resources.getString(R.string.there_are_no_such_vacancies)
                binding.counterVacanciesTextView.isVisible = true
            }
        }
    }


    private fun setOnClicksAndActions() {

        binding.searchScreenEditText.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event != null) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        if (event.rawX >= (binding.searchScreenEditText.right - binding.searchScreenEditText.compoundDrawables[2].bounds
                                .width())
                        ) {
                            viewModel.clearSearchInput()
                            binding.searchScreenEditText.setText(DEFAULT_TEXT)
                            return true
                        }
                    }
                }
                return false
            }
        })

        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(
            binding.searchScreenEditText.windowToken, 0
        )
        binding.searchScreenEditText.setText(DEFAULT_TEXT)

        binding.searchScreenHeaderFilterImageView.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filteringSettingsFragment)
        }
    }

    private fun setOnTextWatchersTextChangeListeners() {

        textWatcher = object : TextWatcherJustOnTextChanged {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setSearchEditTextEndDrawable(s)
                inputSearchText = binding.searchScreenEditText.text.toString()

                if (binding.searchScreenEditText.hasFocus() && (s?.isEmpty() == true || s?.isBlank() == true)) {
                    viewModel.clearSearchInput()
                }
                viewModel.searchDebounced(
                    changedText = s?.toString() ?: ""
                )
            }
        }

        textWatcher?.let { binding.searchScreenEditText.addTextChangedListener(it) }
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

    private fun setSearchEditTextEndDrawable(charSequence: CharSequence?) {
        if (charSequence.isNullOrEmpty()) {
            binding.searchScreenEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, R.drawable.ic_search, 0
            )
        } else {
            binding.searchScreenEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, R.drawable.ic_cross, 0
            )
        }
    }

    private fun emptyScreen() {
        binding.counterVacanciesTextView.isVisible = false
        binding.searchScreenRecyclerView.isVisible = false
        binding.searchScreenFirstLoadingProgressBar.isVisible = false
        binding.searchScreenPaginationProgressBar.isVisible = false
        binding.placeholderSearchVacanciesImageView.isVisible = false
        binding.searchScreenNoInternetPlaceholder.isVisible = false
        binding.searchScreenNothingFoundPlaceholder.isVisible = false
        binding.searchScreenServerErrorPlaceholder.isVisible = false
    }

    companion object {
        const val DEFAULT_TEXT = ""
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}