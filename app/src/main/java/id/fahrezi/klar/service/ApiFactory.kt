package id.fahrezi.klar.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import id.fahrezi.klar.util.NullConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiFactory(accessToken:String){
    val BASE_URL="http://karyalu.id:3000/"

    private  val authInterceptor=Interceptor{chain->
        val newUrl = chain.request()
            .url()
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .header(
                "Authorization",
                "Bearer $accessToken"
            )
            .build()
        chain.proceed(newRequest)
    }
    var logging = HttpLoggingInterceptor()

    private val client = OkHttpClient().newBuilder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    fun main(): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(NullConverterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()



    val api: Api = main().create(Api::class.java)
}