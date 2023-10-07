package ru.practicum.android.diploma.favorites.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_vacancies_table")
class FavoritesVacancyEntity(
    @PrimaryKey
    val id: Int,
    val name: String, // "name": "Секретарь",
    val area: String?,
    val employer: String?, // Информация о компании работодателя
    val salary: String?, // Зарплата
    val experience: String?, // Опыт работы
    val employment: String?, // Тип занятости
    val description: String?, // string html
    val keySkills: String?, // (Ключевые навыки) Список ключевых навыков, не более 30
    val schedule: String?, // График работы
    val contacts: String?,
    val createdAt: Long // дата создания записи для сортировки
)