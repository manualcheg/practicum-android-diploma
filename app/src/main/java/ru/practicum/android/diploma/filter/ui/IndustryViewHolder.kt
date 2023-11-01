package ru.practicum.android.diploma.filter.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemSectorBinding
import ru.practicum.android.diploma.filter.ui.model.IndustryUi

class IndustryViewHolder(
    private val binding: ItemSectorBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(items: IndustryUi) {
        binding.industryItemTextView.text = items.name
        if (items.isSelected) {
            binding.industryImageView.setImageResource(R.drawable.checkbox_circle_checked)
        } else {
            binding.industryImageView.setImageResource(R.drawable.checkbox_circle_unchecked)
        }
    }
}