package id.fahrezi.klar.service.model.Response

data class ErrorResponse(
    val code: String,
    val httpStatusCode: Int,
    val message: String
)