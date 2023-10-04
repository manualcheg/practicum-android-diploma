package ru.practicum.android.diploma.vacancy.data.model.dto

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("240")
    val logo240: String?, // URL логотипа с размером менее 240px по меньшей стороне
    @SerializedName("90")
    val logo90: String, // URL логотипа с размером менее 90px по меньшей стороне
    val original: String // URL необработанного логотипа
)