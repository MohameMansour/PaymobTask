package com.example.paymobtask.data.remotedatasource

import android.annotation.SuppressLint
import android.preference.PreferenceManager
import com.example.paymobtask.data.apis.MovieAPIs
//import com.example.paymobtask.BuildConfig
//import com.expertapps.bontech.data.remotedatasource.RetryInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitBuilder {
    private var instance: Retrofit? = null
    private val cache: PreferenceManager by KoinJavaComponent.inject(PreferenceManager::class.java)
    fun getInstance(): Retrofit {
        return instance ?: createRetrofit().also { instance = it }
    }

    private fun createRetrofit(): Retrofit {
        val gson = GsonBuilder()
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/").apply {
            addConverterFactory(ScalarsConverterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson.create()))
            client(okHttpClient)
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            client(getClientOkHttpInstance())
        }.build()
    }

    fun provideMovieListApis(): MovieAPIs = createRetrofit().create(MovieAPIs::class.java)


    private var baseApi: String = "https://diwandevapi.expertapps.com.sa/Files/Attachement/Res/"
    fun injectBaseApi(api: String) {
        baseApi = api
    }

    val okHttpClient = OkHttpClient.Builder()
        //.addInterceptor(RetryInterceptor(3))  // Set max retries here
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun unSafeOkHttpClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out java.security.cert.X509Certificate>?,
                    authType: String?
                ) {

                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out java.security.cert.X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }

            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier(HostnameVerifier { _, _ -> true })
            }
            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }

    private fun getClientOkHttpInstance(): OkHttpClient {
        val userLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }


        val okHttp = findMostSuitHttpBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                request.add("Accept") {
                    "application/json"
                }
                request.add("Lang") { "en-US" }
                request.add("ClientType") { "Android" }
                request.auth {
                    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZmI0M2Q2NzRjNDIxZGFiMWJhOWI3MDUzMDBjZWEwMSIsIm5iZiI6MTcyOTA5MTQ4OC45Nzg4Mywic3ViIjoiNWU1NzliYzFhOTNkMjUwMDE1NTMxZDBhIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.-9vTsQqRAKLbsCVCi58qgR8b1beOckgCspOQFIBfvF4"
                }
                chain.proceed(request.build())
            }
            .callTimeout(30, TimeUnit.SECONDS) // default is 0 seconds
            .readTimeout(30, TimeUnit.SECONDS) // default is 10 seconds
            .connectTimeout(30, TimeUnit.SECONDS) // default is 10 seconds
            .writeTimeout(30, TimeUnit.SECONDS) // default is 10 seconds
            .addInterceptor(userLoggingInterceptor)
        return okHttp.build()
    }

    private fun findMostSuitHttpBuilder() = when (android.os.Build.VERSION.SDK_INT in 24..25) {
        true -> unSafeOkHttpClient()
        false -> OkHttpClient.Builder()
    }

}

fun Request.Builder.add(key: String, value: () -> String?) = apply {
    value.invoke().also {
        if (!it.isNullOrEmpty()) {
            addHeader(key, it)
        }
    }
}

fun Request.Builder.auth(value: () -> String?) = apply {
    value.invoke().also {
        if (!it.isNullOrEmpty()) {
            addHeader("Authorization", "Bearer $it")
        }
    }
}

fun Request.Builder.remove(key: String) = apply {
    removeHeader(key)
}
