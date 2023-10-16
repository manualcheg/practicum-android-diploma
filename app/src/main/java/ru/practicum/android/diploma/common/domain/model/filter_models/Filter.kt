package ru.practicum.android.diploma.common.domain.model.filter_models

data class Filter(
    val country: CountryFilter?,
    val area: AreaFilter?,
    val industry: IndustryFilter?,
    val salary: Int?,
    val onlyWithSalary: Boolean = false
)
