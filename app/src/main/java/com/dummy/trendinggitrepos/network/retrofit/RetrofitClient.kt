package com.dummy.trendinggitrepos.network.retrofit

import com.dummy.trendinggitrepos.utility.Constants
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        const val BASE_URL = "https://github.com/"
    }

    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        ).client(okHttpClient).build().create(api)
    }

    private val okHttpClient: OkHttpClient
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            return OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                    request.method(chain.request().method, chain.request().body)
                    request.addHeader("Content-Type", "application/json")
                    request.build()
                    chain.proceed(request.build())
                }).readTimeout(Constants.READ_TIME_OUT, TimeUnit.SECONDS).build()
        }
}