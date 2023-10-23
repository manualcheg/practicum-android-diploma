package ru.practicum.android.diploma.common.domain.model.filter_models

import android.os.Parcel
import android.os.Parcelable

data class IndustryFilter(
    var id: Int,
    var industryId: String,
    var name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(industryId)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IndustryFilter> {
        override fun createFromParcel(parcel: Parcel): IndustryFilter {
            return IndustryFilter(parcel)
        }

        override fun newArray(size: Int): Array<IndustryFilter?> {
            return arrayOfNulls(size)
        }
    }
}