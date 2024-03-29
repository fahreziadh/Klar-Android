package id.fahrezi.klar.service

import id.fahrezi.klar.service.model.Request.*
import id.fahrezi.klar.service.model.Response.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
import java.lang.Class

interface Api {

    //  Auth
    @POST("api/register")
    fun register(@Body request: RegisterRequest): Deferred<Response<AuthResponse>>

    @POST("api/signin")
    fun login(@Body request: LoginRequest): Deferred<Response<AuthResponse>>

    @PUT("api/user")
    fun changeProfile(@Body request: ChangeProfileRequest): Deferred<Response<String>>

    //    Class
    @POST("api/class")
    fun createClass(@Body request: ClassRequest): Deferred<Response<id.fahrezi.klar.service.model.Response.Class>>

    @GET("api/class")
    fun getListClass(): Deferred<Response<List<ClassResponse>>>

    @POST("api/class/join")
    fun joinClass(@Body request: JoinClassRequest):Deferred<Response<String>>

    @GET("api/class/{classid}")
    fun classDetail():Deferred<Response<ClassResponse>>

    @POST("api/schedule")
    fun createSchedule(@Body request:ScheduleRequest):Deferred<Response<String>>

    @GET("api/schedule")
    fun listSchedule(@Query("today") today:String):Deferred<Response<List<ScheduleResponse>>>

    @GET("api/attendance")
    fun listAttendance(@Query("scheduleid") scheduleId:String):Deferred<Response<List<AttendanceResponse>>>

    @POST("api/attendance")
    fun inputAttendance(@Body request:AttendanceRequest):Deferred<Response<String>>

    @GET("api/student")
    fun listStudent(@Query("classid")classId:String):Deferred<Response<List<StudentResponse>>>
}