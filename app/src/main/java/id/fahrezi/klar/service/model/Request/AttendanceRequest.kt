package id.fahrezi.klar.service.model.Request

data class AttendanceRequest(
    val attendance: Boolean,
    val scheduleid: String,
    val student: Student
)