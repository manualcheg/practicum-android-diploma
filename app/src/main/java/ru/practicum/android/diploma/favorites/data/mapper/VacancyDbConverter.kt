package ru.practicum.android.diploma.favorites.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.common.domain.model.vacancy_models.AreaVacancy
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Contacts
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Employer
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Employment
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Experience
import ru.practicum.android.diploma.common.domain.model.vacancy_models.KeySkill
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Salary
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Schedule
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.favorites.data.db.FavoritesVacancyEntity
import java.util.Calendar

class VacancyDbConverter {

    fun map(vacancyEntity: FavoritesVacancyEntity): Vacancy {
        vacancyEntity.apply {
            return Vacancy(
                id = id,
                name = name,
                area = Gson().fromJson(area, object : TypeToken<AreaVacancy>() {}.type),
                employer = Gson().fromJson(employer, object : TypeToken<Employer>() {}.type),
                salary = Gson().fromJson(salary, object : TypeToken<Salary>() {}.type),
                experience = Gson().fromJson(experience, object : TypeToken<Experience>() {}.type),
                employment = Gson().fromJson(employment, object : TypeToken<Employment>() {}.type),
                description = description,
                keySkills = Gson().fromJson(
                    keySkills,
                    object : TypeToken<List<KeySkill>>() {}.type
                ),
                schedule = Gson().fromJson(schedule, object : TypeToken<Schedule>() {}.type),
                contacts = Gson().fromJson(contacts, object : TypeToken<Contacts>() {}.type),
            )
        }
    }

    fun map(vacancy: Vacancy): FavoritesVacancyEntity {
        vacancy.apply {
            return FavoritesVacancyEntity(
                id = id,
                name = name,
                area = Gson().toJson(area),
                employer = Gson().toJson(employer),
                salary = Gson().toJson(salary),
                experience = Gson().toJson(experience),
                employment = Gson().toJson(employment),
                description = description,
                keySkills = Gson().toJson(keySkills),
                schedule = Gson().toJson(schedule),
                contacts = Gson().toJson(contacts),
                createdAt = Calendar.getInstance().timeInMillis
            )
        }
    }
}