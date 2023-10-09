package ru.practicum.android.diploma.region_and_sector.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.region_and_sector.ui.model.CountryUi

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onSelectCountryButtonClicked: (CountryUi) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CountryUi) {
        binding.countryItemTextView.text = item.name
        binding.selectCountryImageView.setOnClickListener {
            onSelectCountryButtonClicked.invoke(item)
        }
    }
}