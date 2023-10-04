package ru.practicum.android.diploma.vacancy.data.model.dto

data class ContactsDto(
    val email: String?, // Электронная почта. Значение поля должно соответствовать формату email.
    val name: String?, // Имя контакта
    val phones: List<PhoneDto>? // Список телефонов для связи
)