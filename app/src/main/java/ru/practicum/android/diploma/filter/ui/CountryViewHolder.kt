package ru.practicum.android.diploma.filter.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryAndRegionBinding
import ru.practicum.android.diploma.filter.ui.model.RegionCountryUi

class CountryViewHolder(
    private val binding: ItemCountryAndRegionBinding,
    private val onSelectCountryButtonClicked: (RegionCountryUi) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RegionCountryUi) {
        binding.countryItemTextView.text = item.name
        binding.selectCountryImageView.setOnClickListener {
            onSelectCountryButtonClicked.invoke(item)
        }
    }
}