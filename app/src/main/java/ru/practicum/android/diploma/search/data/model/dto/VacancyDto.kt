package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: Int,
    val name: String, // "name": "Секретарь",
    val area: AreaDto,
    val employer: EmployerDto?, // Информация о компании работодателя
    val salary: SalaryDto?, // Зарплата
    val experience: ExperienceDto?, // Опыт работы
    val employment: EmploymentDto?, // Тип занятости
    val description: String, // string html
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>, // (Ключевые навыки) Список ключевых навыков, не более 30
    val schedule: ScheduleDto?, // График работы
    val contacts: ContactsDto?,
)