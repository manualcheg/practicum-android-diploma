package ru.practicum.android.diploma.common.ui.model

data class VacancyUi(
    val id: Int,
    val name: String, // "name": "Секретарь",
    val areaName: String = "",
    val employerLogoUrl240: String = "", // cсылки на логотипы работодателя разных размеров
    val employerLogoUrl90: String = "",
    val employerLogoUrlOriginal: String = "",
    val employerName: String = "", // Название компании "HeadHunter"
    val salaryCurrency: String = "", // Зарплата
    val salaryFrom: String = "",
    val salaryTo: String = "",
    val experienceName: String = "", // Опыт работы ("От 1 года до 3 лет")
    val employmentName: String = "", // Тип занятости ("Полная занятость")
    val description: String = "", // string html
    val keySkills: List<String> = emptyList(), // (Ключевые навыки) Список ключевых навыков, не более 30
    val scheduleName: String = "", // График работы (Удаленныая работа)
    val contactsEmail: String = "", // Электронная почта. Значение поля должно соответствовать формату email.
    val contactsName: String = "", // Имя контакта
    val contactsPhones: List<PhoneUi> = emptyList()// Список телефонов для связи
)