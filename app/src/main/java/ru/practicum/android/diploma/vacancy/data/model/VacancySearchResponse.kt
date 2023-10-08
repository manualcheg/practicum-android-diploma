package ru.practicum.android.diploma.vacancy.data.model

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.ContactsDto
import ru.practicum.android.diploma.search.data.model.dto.EmployerDto
import ru.practicum.android.diploma.search.data.model.dto.EmploymentDto
import ru.practicum.android.diploma.search.data.model.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.model.dto.KeySkillDto
import ru.practicum.android.diploma.search.data.model.dto.SalaryDto
import ru.practicum.android.diploma.search.data.model.dto.ScheduleDto
import ru.practicum.android.diploma.search.data.network.Response

class VacancySearchResponse(
    val id: Int,
    val name: String, // "name": "Секретарь",
    val area: AreaDto?,
    val employer: EmployerDto?, // Информация о компании работодателя
    val salary: SalaryDto?, // Зарплата
    val experience: ExperienceDto?, // Опыт работы
    val employment: EmploymentDto?, // Тип занятости
    val description: String?, // string html
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>?, // (Ключевые навыки) Список ключевых навыков, не более 30
    val schedule: ScheduleDto?, // График работы
    val contacts: ContactsDto?,
) : Response()