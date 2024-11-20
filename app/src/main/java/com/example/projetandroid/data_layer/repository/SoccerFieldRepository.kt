package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.api.SoccerFieldAPI
import com.example.projetandroid.fromStateCodeToDeveloperMessage
import com.example.projetandroid.model.SignUpModel
import com.example.projetandroid.model.SoccerField
import com.example.projetandroid.model.TokenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SoccerFieldRepository @Inject constructor(
    private val SoccerFieldAPI: SoccerFieldAPI
) : SoccerFieldRepositoryStandards {
    override fun create(token: String, soccerField: SoccerField): Flow<Events<SoccerField?>> =
        flow {
            emit(Events.LoadingEvent(data = null))
            val response = SoccerFieldAPI.create(TOKEN_TYPE + token, soccerField)

            if (!response.isSuccessful) {
                /* return true status code in 200 & 300 */
                /* handel errors between [0..199] and [300 ..[ */
                if (response.code() in intArrayOf(400, 422)) {
                    /* known status code */
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

    override fun update(token: String, soccerField: SoccerField): Flow<Events<SoccerField?>> =
        flow {
            emit(Events.LoadingEvent(data = null))
            val response = SoccerFieldAPI.update(TOKEN_TYPE + token, soccerField)

            if (!response.isSuccessful) {
                /* return true status code in 200 & 300 */
                /* handel errors between [0..199] and [300 ..[ */
                if (response.code() in intArrayOf(400, 422)) {
                    /* known status code */
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


    // not implemented for security raison :|
    override fun delete(token: String, soccerField: SoccerField): Flow<Events<SoccerField?>> {
        TODO("Not yet implemented")
    }


    override fun own(token: String): Flow<Events<SoccerField?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = SoccerFieldAPI.own(TOKEN_TYPE + token)

        if (!response.isSuccessful) {
            emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
        } else {
            if (response.body()?.status == false) {
                emit(
                    Events.ErrorEvent(
                        error = fromStateCodeToDeveloperMessage(
                            response.code(),
                            replacement = response.body()?.error?.joinToString { "\n" }
                                ?: "unexpected error just happened"),
                    ))
            } else {
                response.body()?.data?.let {
                    emit(Events.SuccessEvent(it))
                } ?: run {
                    emit(Events.ErrorEvent(error = "HNSF")) // work with have no soccer field
                }
            }
        }
    }

    override fun allSoccerFields(): Flow<Events<List<SoccerField>?>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = SoccerFieldAPI.allSoccerFields()
        if (!response.isSuccessful) {
            /* return true status code in 200 & 300 */
            /* handel errors between [0..199] and [300 ..[ */
            if (response.code() in intArrayOf(400, 422)) {
                /* known status code */
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
}