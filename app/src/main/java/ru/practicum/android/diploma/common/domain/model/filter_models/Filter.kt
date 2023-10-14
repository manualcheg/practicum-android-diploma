package ru.practicum.android.diploma.common.domain.model.filter_models

data class Filter(
    val country: Country?,
    val area: Area?,
    val industry: Industry?,
    val salary: Int?,
    val onlyWithSalary: Boolean = false
)
