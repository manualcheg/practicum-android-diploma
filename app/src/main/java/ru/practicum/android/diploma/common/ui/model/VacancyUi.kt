package ru.practicum.android.diploma.common.ui.model

data class VacancyUi(
    var id: Int,
    val name: String,
    val areaName: String = "",
    val employerLogoUrl240: String = "",
    val employerLogoUrl90: String = "",
    val employerLogoUrlOriginal: String = "",
    val employerName: String = "",
    val salaryAmount: String = "",
    val experienceName: String = "",
    val employmentName: String = "",
    val description: String = "",
    val keySkills: List<String> = emptyList(),
    val scheduleName: String = "",
    val contactsEmail: String = "",
    val contactsName: String = "",
    val contactsPhones: List<PhoneUi> = emptyList()
)