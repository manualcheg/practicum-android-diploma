package ru.practicum.android.diploma.common.ui.model

import ru.practicum.android.diploma.common.util.recycleView.ItemUiBase

data class PhoneUi (
    override var id: Int,
    val formattedNumber: String,
    val city: String,
    val comment: String,
    val country: String,
    val number: String
) : ItemUiBase()