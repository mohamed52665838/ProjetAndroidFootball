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
        name: String,
        lastName: String,
        password: String,
        email: String,
        role: String,
        phone: String,
    ): Flow<Events<TokenModel?>>

    fun sendOtp(email: String): Flow<Events<Message>>
    fun verifyOtp(email: String, otp: String): Flow<Events<Message>>
    fun logout(): Flow<Events<Message>>
    fun currentUser(): Flow<Events<User>>
    fun updateCurrentUser(updateModel: UpdateModel): Flow<Events<User>>
    fun deleteAccount(id: String): Flow<Events<User>>


    // emitter -> receiver
    // receiver subscribe to emitter


}


