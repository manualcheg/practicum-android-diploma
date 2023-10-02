package ru.practicum.android.diploma.common.domain.model.vacancy_models
data class Vacancy(
    val id: Int,
    val name: String, // "name": "Секретарь",
    val employer: Employer?, // Информация о компании работодателя
    val salary: Salary?, // Зарплата
    val experience: Experience?, // Опыт работы
    val employment: Employment?, // Тип занятости
    val description: String, // string html
    val keySkills: List<KeySkill>, // (Ключевые навыки) Список ключевых навыков, не более 30
    val schedule: Schedule?, // График работы
    val contacts: Contacts?,
)