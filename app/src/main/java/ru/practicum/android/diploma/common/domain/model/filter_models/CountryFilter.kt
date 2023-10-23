package ru.practicum.android.diploma.common.domain.model.filter_models

import java.io.Serializable

data class CountryFilter(
    var id: Int,
    var name: String
) : Serializable
