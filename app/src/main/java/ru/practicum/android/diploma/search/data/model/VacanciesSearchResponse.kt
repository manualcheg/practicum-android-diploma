package ru.practicum.android.diploma.search.data.model

import ru.practicum.android.diploma.search.data.model.dto.VacancyDto
import ru.practicum.android.diploma.search.data.network.Response

class VacanciesSearchResponse(
    val page: Int,
    val pages: Int,
    val found: Int,
    val items: List<VacancyDto>
) : Response()