package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.api.MatchAPI
import com.example.projetandroid.fromStateCodeToDeveloperMessage
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.match.createModel.CreateMatchModelRequest
import com.example.projetandroid.model.match.createModel.CreateMatchModelResponse
import com.example.projetandroid.model.match.joinMatch.AcceptResponse
import com.example.projetandroid.model.match.joinMatch.AllJoinedMatchModel
import com.example.projetandroid.model.match.joinMatch.JointedMatchX
import com.example.projetandroid.model.match.joinMatch.RefuseModel
import com.example.projetandroid.model.match.matchModel.MatchModelReponse
import com.example.projetandroid.model.match.matchModel.PlayersOfMatch
import com.example.projetandroid.model.match.ownMatchesModel.OwnMatchsModel
import com.example.projetandroid.model.terrrain.TerrrainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MatchRepository @Inject constructor(
    private val matchAPI: MatchAPI
) : MatchRepositoryStandards {

    override fun createMatch(
        token: String,
        createMatchModelRequest: CreateMatchModelRequest
    ): Flow<Events<CreateMatchModelResponse>> =
        flow {
            emit(Events.LoadingEvent(data = null))
            val response = matchAPI.createMatch(TOKEN_TYPE + token, createMatchModelRequest)

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

    override fun joinMatch(token: String, matchId: String): Flow<Events<PlayersOfMatch>> =

        flow {
            emit(Events.LoadingEvent(data = null))
            val response = matchAPI.join(TOKEN_TYPE + token, matchId)

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


    override fun getAllJointedMatch(token: String): Flow<Events<AllJoinedMatchModel>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = matchAPI.getAllJointedMatch(TOKEN_TYPE + token)

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


    override fun getMyOwnMatches(token: String): Flow<Events<OwnMatchsModel>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = matchAPI.getMyOwnMatches(TOKEN_TYPE + token)

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

    override fun getMatch(token: String, matchId: String): Flow<Events<MatchModelReponse>> = flow {

        emit(Events.LoadingEvent(data = null))
        val response = matchAPI.getMatch(TOKEN_TYPE + token, matchId)

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
            } else {
                emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
            }
        } else {
            // status code in [200, 300]
            /* 90% the body is exists */
            response.body()?.let { responseType ->
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

    override fun getMatches(token: String): Flow<Events<List<MatchModelReponse>>> =
        flow {

            emit(Events.LoadingEvent(data = null))
            val response = matchAPI.matches(TOKEN_TYPE + token)

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
                } else {
                    emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
                }
            } else {
                // status code in [200, 300]
                /* 90% the body is exists */
                response.body()?.let { responseType ->
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


    override fun acceptMatch(token: String, playerMatchId: String): Flow<Events<AcceptResponse>> =
        flow {

            emit(Events.LoadingEvent(data = null))
            val response = matchAPI.accepteUser(TOKEN_TYPE + token, playerMatchId)

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
                } else {
                    emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
                }
            } else {
                // status code in [200, 300]
                /* 90% the body is exists */
                response.body()?.let { responseType ->
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

    override fun refuseMatch(token: String, playerMatchId: String): Flow<Events<RefuseModel>> =
        flow {

            emit(Events.LoadingEvent(data = null))
            val response = matchAPI.refuseUser(TOKEN_TYPE + token, playerMatchId)

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
                } else {
                    emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
                }
            } else {
                // status code in [200, 300]
                /* 90% the body is exists */
                response.body()?.let { responseType ->
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

    override fun getAllTerrain(token: String): Flow<Events<List<TerrrainModel>>> =

        flow {

            emit(Events.LoadingEvent(data = null))
            val response = matchAPI.getAllTerrain(TOKEN_TYPE + token)

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
                } else {
                    emit(Events.ErrorEvent(error = fromStateCodeToDeveloperMessage(response.code())))
                }
            } else {
                // status code in [200, 300]
                /* 90% the body is exists */
                response.body()?.let { responseType ->
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