package com.lastpro.taskmate.network

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    const val BASE_URL = "https://next-goose-more.ngrok-free.app/laravel-taskmate/public/api/"
    //const val BASE_URL = "https://dev.uinsgd.site/api/index.php/mhs/"

    val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("Accept", "application/json").build()
            chain.proceed(request)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    fun retrofit(token: String?): Retrofit {
        val httpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Access-Control-Allow-Credentials", "true")
                    .addHeader("Authorization", "Bearer "+token)
                    .build()
            chain.proceed(request)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }
}

object ApiClient {
    fun apiService(token: String?): ApiService {
        return RetrofitClient.retrofit(token).create(ApiService::class.java)
    }

    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}
