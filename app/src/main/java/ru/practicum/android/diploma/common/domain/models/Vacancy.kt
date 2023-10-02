package ru.practicum.android.diploma.common.domain.models

data class Vacancy(
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

data class Employer(
    val alternateUrl: String?, // ссылка на представление компании на сайте: "https://hh.ru/employer/1455"
    val blacklisted: Boolean, // Добавлены ли все вакансии работодателя в список скрытых
    val id: String?, // Идентификатор компании
    val logoUrls: LogoUrls?, // cсылки на логотипы работодателя разных размеров
    val name: String, // Название компании "HeadHunter"
    val trusted: Boolean, // флаг, показывающий, прошла ли компания проверку на сайте
    val url: String? // URL, на который нужно сделать GET-запрос, чтобы получить информацию о компании
)

data class LogoUrls(
    val logo240: String?, // URL логотипа с размером менее 240px по меньшей стороне
    val logo90: String, // URL логотипа с размером менее 90px по меньшей стороне
    val original: String // URL необработанного логотипа
)

data class Salary(
    val currency: String?, // Код валюты из справочника currency: "RUR",
    val from: Int?, // Нижняя граница зарплаты: 30000,
    val gross: Boolean?, // Признак что границы зарплаты указаны до вычета налогов: true,
    val to: Int? // Верхняя граница зарплаты: null
)

data class Experience(
    val id: String, // требуемый опыт работы из справочника experience: "between1And3"
    val name: String // название опыта работы: "От 1 года до 3 лет"
)

data class Employment(
    val id: String, // тип занятости из справочника employment: "full"
    val name: String // название типа занятости: "Полная занятость"
)

data class KeySkill(
    val name: String // название ключевого навыка: "Первичный документооборот"
)

data class Schedule(
    val id: String, // график работы из справочника schedule: "fullDay"
    val name: String // Название графика работы: "Полный день" | "Удаленная работа"
)

data class Contacts(
    val email: String?, // Электронная почта. Значение поля должно соответствовать формату email.
    val name: String?, // Имя контакта
    val phones: List<Phone>? // Список телефонов для связи
)

data class Phone(
    val city: String, // код города: "985"
    val comment: String?, // комментарий
    val country: String, // код страны: "7"
    val number: String // номер телефона: "000-00-00"
)
