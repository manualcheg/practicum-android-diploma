package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    val id: String?, // Идентификатор компании
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?, // cсылки на логотипы работодателя разных размеров
    val name: String, // Название компании "HeadHunter"
)