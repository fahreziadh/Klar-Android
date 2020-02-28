package id.fahrezi.klar.service.repository

import com.squareup.moshi.Moshi
import id.fahrezi.klar.service.Api
import id.fahrezi.klar.service.model.Request.LoginRequest
import id.fahrezi.klar.service.model.Request.RegisterRequest
import id.fahrezi.klar.service.model.Response.AuthResponse
import id.fahrezi.klar.service.model.Response.ErrorResponse
import retrofit2.Response

class AuthRepository(private val api: Api) : BaseRepository() {
    suspend fun register(request: RegisterRequest): Response<AuthResponse> {
        val res = api.register(request).await()
        return res
    }
    suspend fun login(request: LoginRequest):Response<AuthResponse>{
        val res = api.login(request).await()
        return res
    }
}