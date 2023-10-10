package ru.practicum.android.diploma.similar_vacancy.data.model

data class SearchSimilarVacancyRequest(
    val vacancyId: Int,
    val options: HashMap<String, String>
)