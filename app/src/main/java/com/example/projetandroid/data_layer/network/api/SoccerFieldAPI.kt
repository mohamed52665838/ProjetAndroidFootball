package com.example.projetandroid.data_layer.network.api

import com.example.projetandroid.Events
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.SoccerField
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface SoccerFieldAPI {

    @POST("/terrain")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body soccerField: SoccerField
    ): Response<ResponseType<SoccerField?>?>

    @PUT("/terrain")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body soccerField: SoccerField
    ): Response<ResponseType<SoccerField?>?>

    @DELETE("/terrain")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Body soccerField: SoccerField
    ): Response<ResponseType<SoccerField?>?>

    @GET("/terrain/mine")
    suspend fun own(@Header("Authorization") token: String): Response<ResponseType<SoccerField?>?>

    @GET("/terrain")
    suspend fun allSoccerFields(): Response<ResponseType<List<SoccerField>?>?>

}