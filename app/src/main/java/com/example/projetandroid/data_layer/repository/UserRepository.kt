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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        val response = userAPI.loginUser(AuthModel(email, password))

        // in login we have 404 user not exits or 401
        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(403, 401, 404, 400)) {
                /* known status code */
                println("we're in the block of known error")
                emit(
                    Events.ErrorEvent(
                        error = "email or password wrong, sorry"
                    )
                )
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }

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
        val response = userAPI.signup(
            SignUpModel(
                name = name,
                lastname = lastName,
                password = password,
                email = email,
                phone = phone,
            )
        )
        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(400, 422)) {
                /* known status code */
                println("we're in the block of known error")
                emit(
                    Events.ErrorEvent(
                        error = "make sure, you have filled all data, if so rapport issue"

                    )
                )
            } else if (response.code() == 409) {
                emit(
                    Events.ErrorEvent(
                        error = "email already exists"
                    )
                )
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }

        }
    }


    override fun sendOtp(email: String): Flow<Events<Message?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = userAPI.sendOTP(SendOTP(email))

        if (!response.isSuccessful) {
            emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }

        }

    }

    override fun verifyOtp(email: String, otp: String): Flow<Events<Message?>> = flow {

        emit(Events.LoadingEvent(data = null))
        val response = userAPI.sendVerify(VerifyOTPModel(email, otp))
        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(403, 401, 404)) {
                /* known status code */
                println("we're in the block of known error")
                emit(
                    Events.ErrorEvent(
                        error = "token expired or wrong"
                    )
                )
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }

        }
    }

    // Authenticated requests
    override fun logout(token: String): Flow<Events<Message?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = userAPI.logout(TOKEN_TYPE + token)
        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(403, 401, 404)) {
                /* known status code */
                println("we're in the block of known error")
                emit(
                    Events.ErrorEvent(
                        error = "email or password wrong, sorry"
                    )
                )
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }

        }
    }

    override fun currentUser(token: String): Flow<Events<User?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = userAPI.currentUser(TOKEN_TYPE + token)
        response.body()?.let {
            if (!response.isSuccessful) {
                /* return true status code in 200 & 300 */
                /* handel errors between [0..199] and [300 ..[ */
                if (response.code() in intArrayOf(403, 401)) {
                    /* known status code */
                    println("we're in the block of known error")
                    emit(
                        Events.ErrorEvent(
                            error = "token expired, login again"
                        )
                    )
                } else {
                    emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
                }
            } else {
                // status code in [200, 300]
                /* 90% the body is exists */
                response.body()?.let { responseType ->
                    // body it self may have errors in this range
                    responseType.error?.let {
                        emit(
                            Events.ErrorEvent(
                                error = fromStateCodeToDeveloperMessage(
                                    response.code(),
                                    replacement = it.joinToString("\n")
                                )
                            )
                        )
                    } ?: run {
                        // in this cause we are sure no errors
                        emit(Events.SuccessEvent(data = responseType.data!!))
                    }

                } ?: run {
                    // empty body suddenly we don't have any indicator
                    emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
                }

            }
        }
    }

    override fun updateCurrentUser(
        token: String,
        updateModel: UpdateModel
    ): Flow<Events<User?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = userAPI.update(TOKEN_TYPE + token, updateModel)


        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(403, 401)) {
                /* known status code */
                println("we're in the block of known error")
                emit(
                    Events.ErrorEvent(
                        error = "token expired, login again"
                    )
                )
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }
        }
    }

    override fun deleteAccount(token: String, id: String): Flow<Events<User?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = userAPI.deleteCurrentUser(TOKEN_TYPE + token, id)
        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(403, 401)) {
                /* known status code */
                println("we're in the block of known error")
                emit(
                    Events.ErrorEvent(
                        error = "token expired login again"
                    )
                )
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
                // body it self may have errors in this range
                responseType.error?.let {
                    emit(
                        Events.ErrorEvent(
                            error = fromStateCodeToDeveloperMessage(
                                response.code(),
                                replacement = it.joinToString("\n")
                            )
                        )
                    )
                } ?: run {
                    // in this cause we are sure no errors
                    emit(Events.SuccessEvent(data = responseType.data!!))
                }

            } ?: run {
                // empty body suddenly we don't have any indicator
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(0)))
            }

        }
    }


}