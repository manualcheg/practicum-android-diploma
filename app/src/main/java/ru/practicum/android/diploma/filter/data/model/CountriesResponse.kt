package ru.practicum.android.diploma.filter.data.model

import ru.practicum.android.diploma.filter.data.model.dto.CountryDto
import ru.practicum.android.diploma.search.data.network.Response

class CountriesResponse(
    val items: List<CountryDto>
) : Response()