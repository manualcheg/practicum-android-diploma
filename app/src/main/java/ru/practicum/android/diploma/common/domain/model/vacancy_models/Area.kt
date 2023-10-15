package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Area(
    val id: Int, //Идентификатор региона из справочника
    val name: String, //Название региона
    val parentId: Int?
)
