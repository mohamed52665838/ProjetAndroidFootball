package com.example.projetandroid.data_layer.network.api

import com.example.projetandroid.model.Message
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.chat_with_stewie.ChatWithStewieModelSendMessage
import com.example.projetandroid.model.chat_with_stewie.StewieResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface StewieAPI {

    @POST("gemini/message")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body content: ChatWithStewieModelSendMessage
    ): Response<ResponseType<StewieResponse?>?>

    @DELETE("gemini/session")
    suspend fun destroySession(
        @Header("Authorization") token: String,
    ): Response<ResponseType<Message?>?>

}