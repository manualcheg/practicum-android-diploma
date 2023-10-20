package ru.practicum.android.diploma.filter.ui.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.filter.ui.model.CountryUi
import ru.practicum.android.diploma.filter.ui.model.FilterUi
import ru.practicum.android.diploma.filter.ui.model.RegionIndustryUi

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

    fun mapCountryFilterToCountryUi(countryFilter: CountryFilter): CountryUi {
        return CountryUi(
            id = countryFilter.id,
            name = countryFilter.name,
        )
    }

    fun mapAreaFilterToRegionIndustryUi(areaFilter: AreaFilter): RegionIndustryUi {
        return RegionIndustryUi(
            id = areaFilter.id,
            name = areaFilter.name,
        )
    }
}