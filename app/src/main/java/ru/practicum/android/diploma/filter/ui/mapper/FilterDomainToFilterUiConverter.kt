package ru.practicum.android.diploma.filter.ui.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.ui.model.FilterUi

class FilterDomainToFilterUiConverter {
    fun mapFilterToFilterUi(filter: Filter?): FilterUi {
        if (filter != null) {
            filter.apply {
                val countryName = country?.name ?: (area?.countryName ?: "")
                return FilterUi(
                    countryName = countryName,
                    areaName = area?.name ?: "",
                    industryName = industry?.name ?: "",
                    salary = salary?.toString() ?: "",
                    onlyWithSalary = onlyWithSalary
                )
            }
        } else return FilterUi()
    }
}