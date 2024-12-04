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
import com.example.projetandroid.runRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MatchRepository @Inject constructor(
    private val matchAPI: MatchAPI
) : MatchRepositoryStandards {


    override fun createMatch(
        token: String,
        createMatchModelRequest: CreateMatchModelRequest
    ): Flow<Events<MatchModelReponse>> = runRequest(
        mapError = mapOf(
            401 to "token expired, login again",
            422 to "wrong data within the body, rapport issue",
            400 to "wrong data within the body, rapport issue"
        )
    ) {
        matchAPI.createMatch(TOKEN_TYPE + token, createMatchModelRequest)
    }

    override fun joinMatch(token: String, matchId: String): Flow<Events<PlayersOfMatch>> =
        runRequest(
            mapError = mapOf(
                422 to "Key not valid, please check your key",
                404 to "Match not exists",
                409 to "You're already joined this match",
                400 to "Match full with players"
            )
        ) {
            matchAPI.join(TOKEN_TYPE + token, matchId)
        }

    override fun jointedMatch(token: String, matchPlayerId: String): Flow<Events<JointedMatchX>> =
        runRequest {
            matchAPI.jointed(TOKEN_TYPE + token, matchPlayerId)
        }// 675021274b0c5c803fc55316


    override fun getAllJointedMatch(token: String): Flow<Events<AllJoinedMatchModel>> = runRequest {
        matchAPI.getAllJointedMatch(TOKEN_TYPE + token)
    }


    override fun getMyOwnMatches(token: String): Flow<Events<OwnMatchsModel>> = runRequest {
        matchAPI.getMyOwnMatches(TOKEN_TYPE + token)
    }


    override fun getMatch(token: String, matchId: String): Flow<Events<MatchModelReponse>> =
        runRequest {
            matchAPI.getMatch(TOKEN_TYPE + token, matchId)
        }


    override fun getMatches(token: String): Flow<Events<List<MatchModelReponse>>> = runRequest {
        matchAPI.matches(TOKEN_TYPE + token)
    }


    override fun acceptMatch(token: String, playerMatchId: String): Flow<Events<AcceptResponse>> =
        runRequest {
            matchAPI.accepteUser(TOKEN_TYPE + token, playerMatchId)
        }

    override fun refuseMatch(token: String, playerMatchId: String): Flow<Events<RefuseModel>> =
        runRequest {
            matchAPI.refuseUser(TOKEN_TYPE + token, playerMatchId)
        }


    override fun getAllTerrain(token: String): Flow<Events<List<TerrrainModel>>> = runRequest {
        matchAPI.getAllTerrain(TOKEN_TYPE + token)
    }


}