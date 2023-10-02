package ru.practicum.android.diploma.common.domain.model.vacancy_models


data class Employer(
    val alternateUrl: String?, // ссылка на представление компании на сайте: "https://hh.ru/employer/1455"
    val blacklisted: Boolean, // Добавлены ли все вакансии работодателя в список скрытых
    val id: String?, // Идентификатор компании
    val logoUrls: LogoUrls?, // cсылки на логотипы работодателя разных размеров
    val name: String, // Название компании "HeadHunter"
    val trusted: Boolean, // флаг, показывающий, прошла ли компания проверку на сайте
    val url: String? // URL, на который нужно сделать GET-запрос, чтобы получить информацию о компании
)
