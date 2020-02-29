package id.fahrezi.klar.service.repository

import com.squareup.moshi.Moshi
import id.fahrezi.klar.service.Api
import id.fahrezi.klar.service.model.Request.*
import id.fahrezi.klar.service.model.Response.*
import retrofit2.Response

class AuthRepository(private val api: Api) : BaseRepository() {
    suspend fun register(request: RegisterRequest): Response<AuthResponse> {
        val res = api.register(request).await()
        return res
    }

    suspend fun login(request: LoginRequest): Response<AuthResponse> {
        val res = api.login(request).await()
        return res
    }

    suspend fun changeProfile(request: ChangeProfileRequest): Response<String> {
        val res = api.changeProfile(request).await()
        return res
    }

    suspend fun listStudent(classid: String): Response<List<StudentResponse>> {
        val res = api.listStudent(classid).await()
        return res
    }

    suspend fun listAttendance(scheduleid: String): Response<List<AttendanceResponse>> {
        val res = api.listAttendance(scheduleid).await()
        return res
    }

    suspend fun inputAttendance(request: AttendanceRequest): Response<String> {
        val res = api.inputAttendance(request).await()
        return res
    }

    suspend fun joinClass(request: JoinClassRequest): Response<String> {
        val res = api.joinClass(request).await()
        return res
    }
}