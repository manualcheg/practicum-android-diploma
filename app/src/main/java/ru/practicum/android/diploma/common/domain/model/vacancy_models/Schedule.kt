package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Schedule(
    val id: String, // график работы из справочника schedule: "fullDay"
    val name: String // Название графика работы: "Полный день" | "Удаленная работа"
)