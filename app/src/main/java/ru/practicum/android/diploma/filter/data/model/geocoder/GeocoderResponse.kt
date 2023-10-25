package ru.practicum.android.diploma.filter.data.model.geocoder

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.network.Response

data class GeocoderResponse(
    @SerializedName("GeoObjectCollection") val geoObjectCollection: GeoObjectCollection?
) : Response()