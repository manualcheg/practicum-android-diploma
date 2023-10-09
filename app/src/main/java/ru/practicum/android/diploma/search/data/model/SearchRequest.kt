package ru.practicum.android.diploma.search.data.model

// отдельный класс для каждого запроса, поля которого
// все параметры для соответствующего запроса, даже если такой параметр всего один
data class SearchRequest(
    val options: HashMap<String, String>,
)