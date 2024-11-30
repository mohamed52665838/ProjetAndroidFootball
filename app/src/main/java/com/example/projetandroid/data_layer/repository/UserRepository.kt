package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.api.UserAPI
import com.example.projetandroid.fromStateCodeToDeveloperMessage
import com.example.projetandroid.model.AuthModel
import com.example.projetandroid.model.Message
import com.example.projetandroid.model.SendOTP
import com.example.projetandroid.model.SignUpModel
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import com.example.projetandroid.model.VerifyOTPModel
import com.example.projetandroid.runRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userAPI: UserAPI
) : UserRepositoryStandards {
    override fun signin(email: String, password: String): Flow<Events<TokenModel>> = runRequest(
        mapError = mapOf(
            404 to "username or password incorrect, please check your credentials",
            400 to "username or password incorrect, please check your credentials",
            401 to "username or password incorrect, please check your credentials",
            403 to "username or password incorrect, please check your credentials"
        )
    ) {
        userAPI.loginUser(AuthModel(email, password))
    }


    override fun signup(
        name: String,
        lastName: String,
        password: String,
        email: String,
        role: String,
        phone: String
    ): Flow<Events<TokenModel?>> = runRequest {
        userAPI.signup(
            SignUpModel(
                name,
                lastName,
                password,
                email,
                role,
                phone
            )
        )
    }


    override fun sendOtp(email: String): Flow<Events<Message?>> = runRequest {
        userAPI.sendOTP(SendOTP(email))
    }


    override fun verifyOtp(email: String, otp: String): Flow<Events<Message?>> = runRequest {
        userAPI.sendVerify(VerifyOTPModel(email, otp))
    }


    // Authenticated requests

    override fun logout(token: String): Flow<Events<Message?>> = runRequest {
        userAPI.logout(TOKEN_TYPE + token)
    }


    override fun currentUser(token: String): Flow<Events<User?>> = runRequest {
        userAPI.currentUser(TOKEN_TYPE + token)
    }


    override fun updateCurrentUser(token: String, updateModel: UpdateModel): Flow<Events<User?>> =
        runRequest {
            userAPI.update(TOKEN_TYPE + token, updateModel)
        }


    override fun deleteAccount(token: String, id: String): Flow<Events<User?>> = runRequest {
        userAPI.deleteCurrentUser(TOKEN_TYPE + token, id)
    }


}