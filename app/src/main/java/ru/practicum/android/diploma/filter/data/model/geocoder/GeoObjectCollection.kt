package ru.practicum.android.diploma.filter.data.model.geocoder

import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember") var featureMember: ArrayList<FeatureMember>
)
