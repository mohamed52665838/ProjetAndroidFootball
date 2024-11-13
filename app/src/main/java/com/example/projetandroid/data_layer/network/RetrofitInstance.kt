package com.example.projetandroid.data_layer.network

import com.example.projetandroid.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    fun getInstance(): Retrofit {
        return Retrofit
            /* add timeout :)*/
            .Builder()
            .client(
                OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS).callTimeout(10, TimeUnit.SECONDS).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}