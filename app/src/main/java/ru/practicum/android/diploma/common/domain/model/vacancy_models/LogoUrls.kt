package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class LogoUrls(
    val logo240: String?, // URL логотипа с размером менее 240px по меньшей стороне
    val logo90: String, // URL логотипа с размером менее 90px по меньшей стороне
    val original: String // URL необработанного логотипа
)