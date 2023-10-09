package ru.practicum.android.diploma.region_and_sector.ui.model

import ru.practicum.android.diploma.common.util.recycleView.ItemUiBase

data class CountryUi(
    override var id: Int,
    val name: String
) : ItemUiBase()