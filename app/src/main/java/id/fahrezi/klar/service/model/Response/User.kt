package id.fahrezi.klar.service.model.Response

data class User(
    val created_at: String,
    val email: String,
    val fullname: String,
    val id: String,
    val imageprofile: String,
    val password: String,
    val username: String
)