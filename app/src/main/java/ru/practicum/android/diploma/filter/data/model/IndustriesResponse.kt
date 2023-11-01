package ru.practicum.android.diploma.filter.data.model

import ru.practicum.android.diploma.filter.data.model.dto.IndustriesDto
import ru.practicum.android.diploma.search.data.network.Response

class IndustriesResponse(
    val items: List<IndustriesDto>
) : Response()