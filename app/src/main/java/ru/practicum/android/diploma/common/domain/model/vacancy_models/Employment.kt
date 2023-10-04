package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Employment(
    val id: String, // тип занятости из справочника employment: "full"
    val name: String // название типа занятости: "Полная занятость"
)