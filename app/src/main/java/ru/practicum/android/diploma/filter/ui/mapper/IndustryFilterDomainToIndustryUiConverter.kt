package ru.practicum.android.diploma.filter.ui.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.filter.ui.model.IndustryUi

class IndustryFilterDomainToIndustryUiConverter {
    fun mapIndustryFilterDomainToIndustryUi(industryFilter: IndustryFilter): IndustryUi {
        return IndustryUi(
            id = industryFilter.id,
            name = industryFilter.name
        )
    }
}