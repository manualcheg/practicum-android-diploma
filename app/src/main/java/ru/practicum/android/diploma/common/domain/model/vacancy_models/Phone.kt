package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Phone(
    val city: String, // код города: "985"
    val comment: String?, // комментарий
    val country: String, // код страны: "7"
    val number: String // номер телефона: "000-00-00"
)