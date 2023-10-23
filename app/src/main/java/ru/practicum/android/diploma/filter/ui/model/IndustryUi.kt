package ru.practicum.android.diploma.filter.ui.model

import ru.practicum.android.diploma.common.util.recycleView.ItemUiBase

data class IndustryUi(
    override var id: Int,
    val name: String,
    override var isSelected: Boolean = false
) : ItemUiBase()