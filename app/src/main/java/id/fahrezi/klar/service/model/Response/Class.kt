package id.fahrezi.klar.service.model.Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Class(
    val classid: String,
    val classname: String,
    val created_at: String,
    val grade: String,
    val id: String,
    val locaiondetail: String,
    val location: String,
    val major: String,
    val owner: Owner
) : Parcelable