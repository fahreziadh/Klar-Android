package id.fahrezi.klar.service.model.Request

import id.fahrezi.klar.service.model.Response.ClassResponse

data class ScheduleRequest(
    val `class`: ClassResponse,
    val detailtime: Detailtime,
    val startdate: String,
    val status: String,
    val totalmonth: Int
)