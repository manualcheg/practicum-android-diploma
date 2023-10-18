package ru.practicum.android.diploma.filter.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemSectorBinding
import ru.practicum.android.diploma.filter.ui.model.RegionIndustryUi

class RegionIndustryViewHolder(
    private val binding: ItemSectorBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(items: RegionIndustryUi) {
        binding.regionAndSectorCheckBox.text = items.name
    }
}