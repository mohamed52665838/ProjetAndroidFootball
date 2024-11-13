package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.model.AuthModel
import com.example.projetandroid.model.Message
import com.example.projetandroid.model.SignUpModel
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryStandards {

    fun signin(email: String, password: String): Flow<Events<TokenModel>>
    fun signup(
        name: String?,
        lastName: String?,
        password: String,
        email: String,
        phone: String?,
    ): Flow<Events<TokenModel?>>

    fun sendOtp(email: String): Flow<Events<Message?>>
    fun verifyOtp(email: String, otp: String): Flow<Events<Message?>>
    fun logout(token: String): Flow<Events<Message?>>
    fun currentUser(token: String): Flow<Events<User?>>
    fun updateCurrentUser(token: String, id: String, updateModel: UpdateModel): Flow<Events<User?>>
    fun deleteAccount(token: String, id: String): Flow<Events<User?>>


    // emitter -> receiver
    // receiver subscribe to emitter


}


