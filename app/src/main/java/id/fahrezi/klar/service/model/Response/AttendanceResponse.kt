package id.fahrezi.klar.service.model.Response

data class AttendanceResponse(
    val attendance: Boolean,
    val created_at: String,
    val id: String,
    val scheduleid: String,
    val student: Student
)