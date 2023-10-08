package ru.practicum.android.diploma.vacancy.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.ui.model.PhoneUi
import ru.practicum.android.diploma.databinding.ItemPhonesBinding

class ContactsPhoneViewHolder(
    private val binding: ItemPhonesBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(phone: PhoneUi) {
        if (phone.comment.isNotBlank()) {
            binding.commentPhoneTextView.text = phone.comment
        } else binding.commentPhoneLinearLayout.isVisible = false

        binding.contactsPhoneTextView.text = phone.formattedNumber
    }
}