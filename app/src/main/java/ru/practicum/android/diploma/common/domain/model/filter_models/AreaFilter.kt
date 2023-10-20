package ru.practicum.android.diploma.common.domain.model.filter_models

import java.io.Serializable

data class AreaFilter(
    var id: Int,
    val name: String,
    val countryName: String
) : Serializable
