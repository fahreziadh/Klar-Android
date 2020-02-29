package id.fahrezi.klar.service.model.Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val fullname: String,
    val imageprofile: String,
    val username: String
) : Parcelable