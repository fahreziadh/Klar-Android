package id.fahrezi.klar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import id.fahrezi.klar.service.ApiFactory
import id.fahrezi.klar.service.model.Request.LoginRequest
import id.fahrezi.klar.service.model.Request.RegisterRequest
import id.fahrezi.klar.service.model.Response.AuthResponse
import id.fahrezi.klar.service.model.Response.ErrorResponse
import id.fahrezi.klar.service.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.coroutines.CoroutineContext

class AuthViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val error = MutableLiveData<ErrorResponse>()
    val auth = MutableLiveData<AuthResponse>()

    fun register(request: RegisterRequest) {
        var repo = AuthRepository(ApiFactory("").api)
        scope.launch {
            var res = repo.register(request)
            if (!res.isSuccessful) {
                error.postValue(getError(res.errorBody()))
                return@launch
            }
            auth.postValue(res.body())
            return@launch
        }
    }

    fun login(request: LoginRequest){
        var repo=AuthRepository(ApiFactory("").api)
        scope.launch {
            var res=repo.login(request)
            if (!res.isSuccessful){
                var err=getError(res.errorBody())
                error.postValue(err)
            }
            auth.postValue(res.body())
            return@launch
        }
    }

    fun getError(errorBody: ResponseBody?): ErrorResponse? {
        var json = errorBody!!.string()
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(ErrorResponse::class.java)
        return jsonAdapter.fromJson(json)
    }
}