package ru.practicum.android.diploma.filter.ui.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.filter.ui.model.AreaCountryUi

class AreaFilterDomainToRegionCountryUiConverter {
    fun mapAreaFilterToRegionCountryUi(areaFilter: AreaFilter): AreaCountryUi {
        return AreaCountryUi(
            id = areaFilter.id,
            name = areaFilter.name
        )
    }
}