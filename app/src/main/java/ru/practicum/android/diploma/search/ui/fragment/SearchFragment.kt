package ru.practicum.android.diploma.search.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.common.util.recycleView.RVAdapter
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.ui.model.ErrorStatusUi
import ru.practicum.android.diploma.search.ui.model.SearchError
import ru.practicum.android.diploma.search.ui.model.SearchState
import ru.practicum.android.diploma.search.ui.model.TextWatcherJustOnTextChanged
import ru.practicum.android.diploma.search.ui.viewModel.SearchViewModel

open class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private var textWatcher: TextWatcher? = null
    protected var vacanciesAdapter: RVAdapter? = null

    private var inputSearchText: String = DEFAULT_TEXT
    protected var isClickAllowed = true
    private var isPaginationAllowed = true

    private var inputMethodManager: InputMethodManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyViews()
    }

    protected open fun initViews() {
        recycleViewInit()
        setOnClicksAndActions()
        setOnTextWatchersTextChangeListeners()
        setOnScrollForRecycleView(
            binding.searchScreenRecyclerView, vacanciesAdapter, viewModel
        )

        viewModel.getButtonState()

        viewModel.observeState().observe(viewLifecycleOwner) {
            renderSearchState(it)
        }

        viewModel.observeErrorToastState().observe(viewLifecycleOwner) {
            renderErrorState(it)
        }

        viewModel.observePaginationLoadingState().observe(viewLifecycleOwner) {
            renderPaginationLoadingState(it)
        }

        viewModel.observeFilterButtonState().observe(viewLifecycleOwner) {
            renderButtonState(it)
        }

        isClickAllowed = true

    }

    protected open fun destroyViews() {
        textWatcher?.let { binding.searchScreenEditText.removeTextChangedListener(it) }
        binding.searchScreenRecyclerView.adapter = null
        vacanciesAdapter = null
        _binding = null
    }

    protected open fun recycleViewInit() {
        vacanciesAdapter = RVAdapter { vacancy ->
            if (isClickDebounce()) {
                val direction =
                    SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancy.id)
                findNavController().navigate(direction)
            }
        }

        binding.searchScreenRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchScreenRecyclerView.adapter = vacanciesAdapter

        binding.searchScreenRecyclerView.itemAnimator = null
    }

    protected fun renderSearchState(state: SearchState) {
        when (state) {
            is SearchState.Error -> showError(state.errorStatus)
            is SearchState.Loading.LoadingSearch -> showLoadingSearch()
            SearchState.Success.Empty -> showEmpty()
            is SearchState.Success.SearchContent -> showContent(state.vacancies, state.foundVacancy)
        }
    }

    protected fun renderErrorState(state: SearchError) {
        when (state) {
            SearchError.ERROR_OCCURRED -> {
                showErrorToast(resources.getString(R.string.failed_to_get_a_list_of_vacancies))
            }

            SearchError.NO_CONNECTION -> {
                showErrorToast(resources.getString(R.string.no_internet))
            }
        }
    }

    protected open fun showErrorToast(message: String) {
        showToast(message)
        binding.searchScreenPaginationProgressBar.isVisible = false
    }

    protected fun renderPaginationLoadingState(isLoading: Boolean) {
        if (isLoading) showLoadingPages()
        else hideLoadingPages()
    }

    private fun renderButtonState(isFiltersExist: Boolean) {
        if (isFiltersExist) {
            setMenuFilterIcon(R.drawable.ic_filters_selected)
        } else setMenuFilterIcon(R.drawable.ic_filters_unselected)
    }

    private fun setMenuFilterIcon(drawableInt: Int) {
        binding.searchVacanciesToolbar.menu.findItem(R.id.searchScreenToolbarFilterMenu).icon =
            AppCompatResources.getDrawable(requireContext(), drawableInt)
    }

    protected open fun showContent(vacancies: List<VacancyUi>, foundVacancies: Int) {
        emptyScreen()
        vacanciesAdapter?.items = vacancies
        binding.counterVacanciesTextView.text = resources.getQuantityString(
            R.plurals.vacancy_plural, foundVacancies, foundVacancies
        )
        binding.counterVacanciesTextView.isVisible = true
    }

    protected open fun showEmpty() {
        emptyScreen()
        vacanciesAdapter?.items = listOf()
        binding.placeholderSearchVacanciesImageView.isVisible = true
    }

    protected open fun showLoadingSearch() {
        emptyScreen()
        vacanciesAdapter?.items = listOf()
        binding.searchScreenFirstLoadingProgressBar.isVisible = true
        inputMethodManager?.hideSoftInputFromWindow(
            binding.searchScreenEditText.windowToken, 0
        )
    }

    protected open fun showLoadingPages() {
        binding.searchScreenPaginationProgressBar.isVisible = true
    }

    protected open fun hideLoadingPages() {
        binding.searchScreenPaginationProgressBar.isVisible = false
    }

    protected open fun showError(errorStatus: ErrorStatusUi) {
        when (errorStatus) {
            ErrorStatusUi.NO_CONNECTION -> {
                vacanciesAdapter?.items = listOf()
                emptyScreen()
                binding.searchScreenNoInternetPlaceholder.isVisible = true
            }

            ErrorStatusUi.ERROR_OCCURRED -> {
                vacanciesAdapter?.items = listOf()
                emptyScreen()
                binding.searchScreenServerErrorPlaceholder.isVisible = true
            }

            ErrorStatusUi.NOTHING_FOUND -> {
                vacanciesAdapter?.items = listOf()
                emptyScreen()
                binding.searchScreenNothingFoundPlaceholder.isVisible = true
                binding.counterVacanciesTextView.text =
                    resources.getString(R.string.there_are_no_such_vacancies)
                binding.counterVacanciesTextView.isVisible = true
            }
        }
    }


    protected open fun setOnClicksAndActions() {
        inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(
            binding.searchScreenEditText.windowToken, 0
        )

        binding.searchFormButton.setOnClickListener {
            binding.searchScreenEditText.setText(DEFAULT_TEXT)
        }
        binding.searchVacanciesToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.searchScreenToolbarFilterMenu) {
                findNavController().navigate(R.id.action_searchFragment_to_filteringSettingsFragment)
            }
            true
        }
    }

    protected fun setOnScrollForRecycleView(
        recyclerView: RecyclerView, adapter: RVAdapter?, viewModel: SearchViewModel
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter?.itemCount
                    if (itemsCount != null) {
                        if (pos >= itemsCount - 1) {
                            if (isPaginationDebounce()) {
                                viewModel.onLastItemReached()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setOnTextWatchersTextChangeListeners() {

        textWatcher = object : TextWatcherJustOnTextChanged {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setSearchEditTextEndDrawable(s)
                inputSearchText = binding.searchScreenEditText.text.toString()

                if (s?.isEmpty() == true) {
                    vacanciesAdapter?.items = listOf()
                    viewModel.clearSearchInput()
                }
                viewModel.searchDebounced(
                    changedText = s?.toString() ?: ""
                )
            }
        }

        textWatcher?.let { binding.searchScreenEditText.addTextChangedListener(it) }
    }

    protected fun isClickDebounce(): Boolean {
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

    protected fun isPaginationDebounce(): Boolean {
        val current = isPaginationAllowed
        if (isPaginationAllowed) {
            isPaginationAllowed = false

            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isPaginationAllowed = true
            }
        }
        return current
    }

    private fun setSearchEditTextEndDrawable(charSequence: CharSequence?) {
        if (charSequence.isNullOrEmpty()) {
            binding.searchFormButton.setImageResource(R.drawable.ic_search)
        } else {
            binding.searchFormButton.setImageResource(R.drawable.ic_cross)
        }
    }

    protected open fun emptyScreen() {
        binding.counterVacanciesTextView.isVisible = false
        binding.searchScreenFirstLoadingProgressBar.isVisible = false
        binding.searchScreenPaginationProgressBar.isVisible = false
        binding.placeholderSearchVacanciesImageView.isVisible = false
        binding.searchScreenNoInternetPlaceholder.isVisible = false
        binding.searchScreenNothingFoundPlaceholder.isVisible = false
        binding.searchScreenServerErrorPlaceholder.isVisible = false
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DEFAULT_TEXT = ""
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}