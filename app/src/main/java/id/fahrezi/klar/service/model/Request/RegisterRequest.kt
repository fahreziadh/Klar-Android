package id.fahrezi.klar.service.model.Request

data class RegisterRequest(
    val email: String,
    val fullname: String,
    val imageprofile: String,
    val password: String,
    val username: String
)