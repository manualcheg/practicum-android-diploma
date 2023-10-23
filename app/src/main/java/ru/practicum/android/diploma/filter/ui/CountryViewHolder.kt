package ru.practicum.android.diploma.filter.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryAndRegionBinding
import ru.practicum.android.diploma.filter.ui.model.CountryUi

class CountryViewHolder(
    private val binding: ItemCountryAndRegionBinding,
    private val onSelectCountryButtonClicked: (CountryUi) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CountryUi) {
        binding.countryItemTextView.text = item.name
        binding.root.setOnClickListener {
            onSelectCountryButtonClicked.invoke(item)
        }
    }
}