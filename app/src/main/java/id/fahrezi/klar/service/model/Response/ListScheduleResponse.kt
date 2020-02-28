package id.fahrezi.klar.service.model.Response

data class ListScheduleResponse(
    val `class`: Class,
    val created_at: String,
    val id: String,
    val startdate: String,
    val starttime: String
)