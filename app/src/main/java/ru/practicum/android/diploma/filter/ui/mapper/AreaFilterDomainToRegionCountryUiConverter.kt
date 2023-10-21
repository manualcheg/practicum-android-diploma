package ru.practicum.android.diploma.filter.ui.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.filter.ui.model.RegionCountryUi

class AreaFilterDomainToRegionCountryUiConverter {
    fun mapAreaFilterToRegionCountryUi(areaFilter: AreaFilter): RegionCountryUi {
        return RegionCountryUi(
            id = areaFilter.id,
            name = areaFilter.name
        )
    }
}