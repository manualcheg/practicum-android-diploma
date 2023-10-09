package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Vacancies (
    val vacancyList: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val found: Int,
)