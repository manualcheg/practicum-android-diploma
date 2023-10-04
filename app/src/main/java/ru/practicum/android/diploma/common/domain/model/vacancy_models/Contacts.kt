package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Contacts(
    val email: String?, // Электронная почта. Значение поля должно соответствовать формату email.
    val name: String?, // Имя контакта
    val phones: List<Phone>? // Список телефонов для связи
)