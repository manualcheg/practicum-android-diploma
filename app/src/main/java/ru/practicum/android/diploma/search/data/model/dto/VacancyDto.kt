package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: Int,
    val name: String, // "name": "Секретарь",
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

data class EmployerDto(
    @SerializedName("alternate_url")
    val alternateUrl: String?, // ссылка на представление компании на сайте: "https://hh.ru/employer/1455"
    val blacklisted: Boolean, // Добавлены ли все вакансии работодателя в список скрытых
    val id: String?, // Идентификатор компании
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?, // cсылки на логотипы работодателя разных размеров
    val name: String, // Название компании "HeadHunter"
    val trusted: Boolean, // флаг, показывающий, прошла ли компания проверку на сайте
    val url: String? // URL, на который нужно сделать GET-запрос, чтобы получить информацию о компании
)

data class LogoUrlsDto(
    @SerializedName("240")
    val logo240: String?, // URL логотипа с размером менее 240px по меньшей стороне
    @SerializedName("90")
    val logo90: String, // URL логотипа с размером менее 90px по меньшей стороне
    val original: String // URL необработанного логотипа
)

data class SalaryDto(
    val currency: String?, // Код валюты из справочника currency: "RUR",
    val from: Int?, // Нижняя граница зарплаты: 30000,
    val gross: Boolean?, // Признак что границы зарплаты указаны до вычета налогов: true,
    val to: Int? // Верхняя граница зарплаты: null
)

data class ExperienceDto(
    val id: String, // требуемый опыт работы из справочника experience: "between1And3"
    val name: String // название опыта работы: "От 1 года до 3 лет"
)

data class EmploymentDto(
    val id: String, // тип занятости из справочника employment: "full"
    val name: String // название типа занятости: "Полная занятость"
)

data class KeySkillDto(
    val name: String // название ключевого навыка: "Первичный документооборот"
)

data class ScheduleDto(
    val id: String, // график работы из справочника schedule: "fullDay"
    val name: String // Название графика работы: "Полный день" | "Удаленная работа"
)

data class ContactsDto(
    val email: String?, // Электронная почта. Значение поля должно соответствовать формату email.
    val name: String?, // Имя контакта
    val phones: List<PhoneDto>? // Список телефонов для связи
)

data class PhoneDto(
    val city: String, // код города: "985"
    val comment: String?, // комментарий
    val country: String, // код страны: "7"
    val number: String // номер телефона: "000-00-00"
)
