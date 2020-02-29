package id.fahrezi.klar.service.model.Request

import id.fahrezi.klar.service.model.Response.StudentResponse

data class AttendanceRequest(
    val attendance: Boolean,
    val scheduleid: String,
    val student: StudentResponse
)