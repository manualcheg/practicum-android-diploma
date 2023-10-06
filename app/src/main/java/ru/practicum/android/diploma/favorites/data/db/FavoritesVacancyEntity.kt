package ru.practicum.android.diploma.favorites.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_vacancies_table")
class FavoritesVacancyEntity(
    @PrimaryKey
    val id: Int,
    val name: String, // "name": "Секретарь",
    val area: AreaEntity?,
    val employer: EmployerEntity?, // Информация о компании работодателя
    val salary: SalaryEntity?, // Зарплата
    val experience: ExperienceEntity?, // Опыт работы
    val employment: EmploymentEntity?, // Тип занятости
    val description: String?, // string html
    val keySkills: List<KeySkillEntity>?, // (Ключевые навыки) Список ключевых навыков, не более 30
    val schedule: ScheduleEntity?, // График работы
    val contacts: ContactsEntity?,
    val createdAt: Long // дата создания записи для сортировки
)
@Entity(tableName = "employer_table")
data class EmployerEntity(
    @PrimaryKey
    val id: String?, // Идентификатор компании
    val logoUrls: LogoUrlsEntity?, // cсылки на логотипы работодателя разных размеров
    val name: String, // Название компании "HeadHunter"
)
@Entity(tableName = "logo_urls_table")
data class LogoUrlsEntity(
    @PrimaryKey
    val logo240: String?, // URL логотипа с размером менее 240px по меньшей стороне
    val logo90: String, // URL логотипа с размером менее 90px по меньшей стороне
    val original: String // URL необработанного логотипа
)
@Entity(tableName = "salary_table")
data class SalaryEntity(
    @PrimaryKey
    val id: String,
    val currency: String?, // Код валюты из справочника currency: "RUR",
    val from: Int?, // Нижняя граница зарплаты: 30000,
    val gross: Boolean?, // Признак что границы зарплаты указаны до вычета налогов: true,
    val to: Int? // Верхняя граница зарплаты: null
)
@Entity(tableName = "experience_table")
data class ExperienceEntity(
    @PrimaryKey
    val id: String, // требуемый опыт работы из справочника experience: "between1And3"
    val name: String // название опыта работы: "От 1 года до 3 лет"
)
@Entity(tableName = "employment_table")
data class EmploymentEntity(
    val id: String, // тип занятости из справочника employment: "full"
    val name: String // название типа занятости: "Полная занятость"
)
@Entity(tableName = "key_skill_table")
data class KeySkillEntity(
    @PrimaryKey
    val name: String // название ключевого навыка: "Первичный документооборот"
)

@Entity(tableName = "shedule_table")
data class ScheduleEntity(
    @PrimaryKey
    val id: String, // график работы из справочника schedule: "fullDay"
    val name: String // Название графика работы: "Полный день" | "Удаленная работа"
)

@Entity(tableName = "contacts_table")
data class ContactsEntity(
    @PrimaryKey
    val id: String,
    val email: String?, // Электронная почта. Значение поля должно соответствовать формату email.
    val name: String?, // Имя контакта
    val phones: List<PhoneEntity>? // Список телефонов для связи
)

@Entity(tableName = "phone_table")
data class PhoneEntity(
    @PrimaryKey
    val id: String,
    val city: String, // код города: "985"
    val comment: String?, // комментарий
    val country: String, // код страны: "7"
    val number: String // номер телефона: "000-00-00"
)

@Entity(tableName = "area_table")
data class AreaEntity(
    @PrimaryKey
    val id: String, //Идентификатор региона из справочника
    val name: String, //Название региона
)