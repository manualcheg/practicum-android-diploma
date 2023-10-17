package ru.practicum.android.diploma.filter.data.model

import ru.practicum.android.diploma.filter.data.model.dto.AreasDto
import ru.practicum.android.diploma.search.data.network.Response

class AreasResponse(
    val items: List<AreasDto>
) : Response()