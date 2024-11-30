package com.example.projetandroid.data_layer.network.api

import com.example.projetandroid.model.AuthModel
import com.example.projetandroid.model.Message
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.SendOTP
import com.example.projetandroid.model.SignUpModel
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import com.example.projetandroid.model.VerifyOTPModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    // no need to authentication
    @POST("/auth/login")
    suspend fun loginUser(@Body loginModel: AuthModel): Response<ResponseType<TokenModel?>?>

    @POST("/auth/signup")
    suspend fun signup(@Body signupModel: SignUpModel): Response<ResponseType<TokenModel?>?>

    @POST("/auth/otp-send")
    suspend fun sendOTP(@Body sendTopModel: SendOTP): Response<ResponseType<Message?>?>

    @POST("/auth/otp-verify")
    suspend fun sendVerify(@Body verifyModel: VerifyOTPModel): Response<ResponseType<Message?>?>
    // end no need to authentication

    @POST("/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<ResponseType<Message?>?>

    @POST("/auth/refresh")
    suspend fun refreshToken(@Body refreshTokenModel: AuthModel): Response<ResponseType<TokenModel?>>

    @PUT("/user")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body updateModel: UpdateModel
    ): Response<ResponseType<User?>?>

    @GET("/user")
    suspend fun currentUser(@Header("Authorization") token: String): Response<ResponseType<User?>?>

    @DELETE("/user/{id}")
    suspend fun deleteCurrentUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<ResponseType<User?>?>
}