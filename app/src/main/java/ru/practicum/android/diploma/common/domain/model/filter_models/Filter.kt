package ru.practicum.android.diploma.common.domain.model.filter_models

data class Filter(
    var country: Country?,
    var area: Area?,
    var industry: Industry?,
    var salary: Int?,
    var onlyWithSalary: Boolean?
)
