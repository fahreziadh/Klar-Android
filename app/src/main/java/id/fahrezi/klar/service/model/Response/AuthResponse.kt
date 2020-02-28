package id.fahrezi.klar.service.model.Response

data class AuthResponse(
    val accesstoken: String,
    val user: User
)