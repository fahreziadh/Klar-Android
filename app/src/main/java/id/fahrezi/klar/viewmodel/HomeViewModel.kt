package id.fahrezi.klar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import id.fahrezi.klar.service.ApiFactory
import id.fahrezi.klar.service.model.Request.ChangeProfileRequest
import id.fahrezi.klar.service.model.Request.JoinClassRequest
import id.fahrezi.klar.service.model.Request.LoginRequest
import id.fahrezi.klar.service.model.Response.ClassResponse
import id.fahrezi.klar.service.model.Response.ErrorResponse
import id.fahrezi.klar.service.model.Response.ScheduleResponse
import id.fahrezi.klar.service.repository.AuthRepository
import id.fahrezi.klar.service.repository.ClassRepository
import id.fahrezi.klar.service.repository.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel() {

    private val _navigateToClassDetail = MutableLiveData<Params>()
    val navigateToClassDetail
        get() = _navigateToClassDetail
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val error = MutableLiveData<ErrorResponse>()
    val changeProfile = MutableLiveData<Boolean>()
    val authenticated = MutableLiveData<Boolean>()
    val listSchedule = MutableLiveData<List<ScheduleResponse>>()
    val listClass = MutableLiveData<List<ClassResponse>>()
    val joinClass = MutableLiveData<Boolean>()

    data class Params(var i: Long, val s: ScheduleResponse)

    init {

    }

    fun changeProfile(request: ChangeProfileRequest, accessToken: String) {
        var repo = AuthRepository(ApiFactory(accessToken).api)
        scope.launch {
            val res = repo.changeProfile(request)
            if (!res.isSuccessful) {
                changeProfile.postValue(false)
                if (res.code() == 401) {
                    authenticated.postValue(false)
                }
            }
            changeProfile.postValue(true)
        }
    }

    fun showListSchedulle(today: String, accessToken: String) {
        var repo = ScheduleRepository(ApiFactory(accessToken).api)
        scope.launch {
            val res = repo.listSchedule(today)
            if (!res.isSuccessful) {
                listSchedule.postValue(null)
                if (res.code() == 401) {
                    authenticated.postValue(false)
                }
            }
            listSchedule.postValue(res.body())
        }
    }

    fun showListClass(accessToken: String) {
        val repo = ClassRepository(ApiFactory(accessToken).api)
        scope.launch {
            var res = repo.listClass()
            if (!res.isSuccessful) {
                listClass.postValue(null)
            }
            listClass.postValue(res.body())
        }
    }

    fun joinClass(classid: String, accessToken: String) {
        var repo = AuthRepository(ApiFactory(accessToken).api)
        scope.launch {
            val res = repo.joinClass(JoinClassRequest(classid))
            if (!res.isSuccessful) {
                joinClass.postValue(false)
                return@launch
            }
            joinClass.postValue(true)
        }
    }

    fun getError(errorBody: ResponseBody?): ErrorResponse? {
        var json = errorBody!!.string()
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(ErrorResponse::class.java)
        return jsonAdapter.fromJson(json)
    }
}