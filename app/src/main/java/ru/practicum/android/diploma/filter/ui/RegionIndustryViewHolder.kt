package ru.practicum.android.diploma.filter.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionAndSectorBinding
import ru.practicum.android.diploma.filter.ui.model.RegionIndustryUi

class RegionIndustryViewHolder(
    private val binding: ItemRegionAndSectorBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(items: RegionIndustryUi) {
        binding.regionAndSectorCheckBox.text = items.name
    }
}