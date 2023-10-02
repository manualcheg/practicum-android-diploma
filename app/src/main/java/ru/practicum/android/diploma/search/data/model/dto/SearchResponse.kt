package ru.practicum.android.diploma.search.data.model.dto

import ru.practicum.android.diploma.search.data.network.Response

// класс описывающий ответ сервера
// наследуется от Response и соответственно имеет так же resultCode
class SearchResponse(
    val items: List<VacancyDto>
) : Response()