package ru.practicum.android.diploma.common.domain.model.vacancy_models

data class Salary(
    val currency: String?, // Код валюты из справочника currency: "RUR",
    val from: Int?, // Нижняя граница зарплаты: 30000,
    val gross: Boolean?, // Признак что границы зарплаты указаны до вычета налогов: true,
    val to: Int? // Верхняя граница зарплаты: null
)