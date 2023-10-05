package ru.practicum.android.diploma.search.data.model.dto

data class PhoneDto(
    val city: String, // код города: "985"
    val comment: String?, // комментарий
    val country: String, // код страны: "7"
    val number: String // номер телефона: "000-00-00"
)