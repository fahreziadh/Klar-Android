package id.fahrezi.klar.service.model.Response

data class ClassResponse(
    val classid: String,
    val classname: String,
    val created_at: String,
    val grade: String,
    val id: String,
    val locaiondetail: String,
    val location: String,
    val major: String,
    val owner: Owner
)
