package ru.practicum.android.diploma.common.util.recycleView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.databinding.ItemVacancyBinding

class VacancyViewHolder(
    private val binding: ItemVacancyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: VacancyUi) {
        if (vacancy.areaName.isNotBlank()) {
            val header = buildString {
                append(vacancy.name)
                append(", ")
                append(vacancy.areaName)
            }
            binding.vacancyHeaderTextView.text = header
        } else binding.vacancyHeaderTextView.text = vacancy.name

        binding.vacancyDescriptionTextView.text = vacancy.employerName
        Glide
            .with(itemView)
            .load(vacancy.employerLogoUrl90)
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(
                        R.dimen.corner_radius
                    )))
            .into(binding.vacancyLayoutLogoImageView)

        binding.vacancySalaryTextView.text = vacancy.salaryAmount
    }
}