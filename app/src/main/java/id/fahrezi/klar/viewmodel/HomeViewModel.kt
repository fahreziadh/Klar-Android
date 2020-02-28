package id.fahrezi.klar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import id.fahrezi.klar.service.ApiFactory
import id.fahrezi.klar.service.model.Request.ChangeProfileRequest
import id.fahrezi.klar.service.model.Request.LoginRequest
import id.fahrezi.klar.service.model.Response.ErrorResponse
import id.fahrezi.klar.service.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel() {

    private val _navigateToClassDetail = MutableLiveData<Long>()
    val navigateToClassDetail
        get() = _navigateToClassDetail
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val error = MutableLiveData<ErrorResponse>()
    val changeProfile = MutableLiveData<Boolean>()

    fun changeProfile(request: ChangeProfileRequest, accessToken: String) {
        var repo = AuthRepository(ApiFactory(accessToken).api)
        scope.launch {
            val res = repo.changeProfile(request)
            if (!res.isSuccessful) {
                changeProfile.postValue(false)
            }
            changeProfile.postValue(true)
        }

    }


    fun getError(errorBody: ResponseBody?): ErrorResponse? {
        var json = errorBody!!.string()
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(ErrorResponse::class.java)
        return jsonAdapter.fromJson(json)
    }
}