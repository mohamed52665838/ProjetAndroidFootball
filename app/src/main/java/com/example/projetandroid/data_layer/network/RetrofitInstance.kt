package com.example.projetandroid.data_layer.network

import com.example.projetandroid.ADDRESS_SEARCH_AP_URI
import com.example.projetandroid.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level


object RetrofitInstance {
    fun getInstance(): Retrofit {
        return Retrofit
            /* add timeout :)*/
            .Builder()
            .client(
                OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS).callTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    fun getAddressLockupRetrofitInstance(): Retrofit {
        return Retrofit
            /* add timeout :)*/
            .Builder()
            .client(
                OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS).callTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ADDRESS_SEARCH_AP_URI)
            .build()
    }
}