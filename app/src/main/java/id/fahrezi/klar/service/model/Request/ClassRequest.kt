package id.fahrezi.klar.service.model.Request

data class ClassRequest(
    val classname: String,
    val grade: String,
    val locaiondetail: String,
    val location: String,
    val major: String,
    val owner: Owner
)