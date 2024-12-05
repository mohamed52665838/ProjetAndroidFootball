package com.example.projetandroid.data_layer.network.api

import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.TokenModel
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenAPI {
    @POST("/auth/refresh")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<ResponseType<TokenModel>>

}