package ru.practicum.android.diploma.common.domain.model.filter_models

data class AreaFilter(
    var id: Int,
    var name: String,
    val parentId: Int?
)
