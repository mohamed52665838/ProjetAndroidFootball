package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
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
import com.example.projetandroid.runNotAuthenticatedRequest
import com.example.projetandroid.runRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userAPI: UserAPI,
    private val pref: ShardPref
) : UserRepositoryStandards {
    override fun signin(email: String, password: String): Flow<Events<TokenModel>> =
        runNotAuthenticatedRequest(
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
    ): Flow<Events<TokenModel?>> = runNotAuthenticatedRequest(
        mapError = mapOf(
            422 to "wrong data within the body, rapport issue",
            400 to "wrong data within the body, rapport issue"
        )
    ) {
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

    override fun sendOtp(email: String): Flow<Events<Message>> = runNotAuthenticatedRequest {
        userAPI.sendOTP(SendOTP(email))
    }

    override fun verifyOtp(email: String, otp: String): Flow<Events<Message>> =
        runNotAuthenticatedRequest {
            userAPI.sendVerify(VerifyOTPModel(email, otp))
        }

    // Authenticated requests
    override fun logout(): Flow<Events<Message>> = runRequest(pref = pref) {
        userAPI.logout(it)
    }

    override fun currentUser(): Flow<Events<User>> = runRequest(pref = pref) {

        userAPI.currentUser(it)
    }

    override fun updateCurrentUser(updateModel: UpdateModel): Flow<Events<User>> =
        runRequest(
            pref = pref,
            mapError = mapOf(
                401 to "token expired, login again",
                422 to "wrong data within the body, rapport issue",
                400 to "wrong data within the body, rapport issue"
            )
        ) {
            userAPI.update(it, updateModel)
        }

    override fun deleteAccount(id: String): Flow<Events<User>> = runRequest(pref = pref) {
        userAPI.deleteCurrentUser(it, id)
    }

}