package ru.practicum.android.diploma.filter.data.model.dto

data class Filter(
    var country: Country?,
    var area: Area?,
    var industry: Industry?,
    var salary: Int?,
    var onlyWithSalary: Boolean?
)
