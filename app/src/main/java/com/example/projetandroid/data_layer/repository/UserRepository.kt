package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.api.UserAPI
import com.example.projetandroid.model.AuthModel
import com.example.projetandroid.model.Message
import com.example.projetandroid.model.SendOTP
import com.example.projetandroid.model.SignUpModel
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import com.example.projetandroid.model.VerifyOTPModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userAPI: UserAPI
) : UserRepositoryStandards {

    override fun signin(
        email: String,
        password: String
    ): Flow<Events<TokenModel>> = flow {
        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.loginUser(AuthModel(email, password))
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))
            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }

    }

    override fun signup(
        name: String?,
        lastName: String?,
        password: String,
        email: String,
        phone: String?,
    ): Flow<Events<TokenModel?>> = flow {
        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.signup(
                SignUpModel(
                    name = name,
                    lastname = lastName,
                    password = password,
                    email = email,
                    phone = phone,
                )
            )
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))
            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }
    }


    override fun sendOtp(email: String): Flow<Events<Message?>> = flow {

        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.sendOTP(SendOTP(email))
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))
            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }
    }

    override fun verifyOtp(email: String, otp: String): Flow<Events<Message?>> = flow {

        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.sendVerify(VerifyOTPModel(email, otp))
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))

            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }

    }

    // Authenticated requests
    override fun logout(token: String): Flow<Events<Message?>> = flow {
        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.logout(TOKEN_TYPE + token)
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))

            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }
    }

    override fun currentUser(token: String): Flow<Events<User?>> = flow {
        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.currentUser(TOKEN_TYPE + token)
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))
            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }
    }

    override fun updateCurrentUser(
        token: String,
        id: String,
        updateModel: UpdateModel
    ): Flow<Events<User?>> = flow {
        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.update(TOKEN_TYPE + token, id, updateModel)
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))
            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }
    }

    override fun deleteAccount(token: String, id: String): Flow<Events<User?>> = flow {
        emit(Events.LoadingEvent(data = null))
        try {
            val response = userAPI.deleteCurrentUser(TOKEN_TYPE + token, id)
            if (response.isSuccessful && response.body() != null) {
                emit(Events.SuccessEvent(data = response.body()!!))
            } else {
                emit(Events.ErrorEvent(error = response.message()))
            }
        } catch (e: Exception) {
            emit(Events.ErrorEvent(error = e.message ?: "unexpected error just happened"))
        }
    }


}