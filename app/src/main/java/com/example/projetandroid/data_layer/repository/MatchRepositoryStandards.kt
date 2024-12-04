package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
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

interface MatchRepositoryStandards {

    fun getMyOwnMatches(token: String): Flow<Events<OwnMatchsModel>>

    fun createMatch(
        token: String,
        createMatchModelRequest: CreateMatchModelRequest
    ): Flow<Events<MatchModelReponse>>

    fun joinMatch(token: String, matchId: String): Flow<Events<PlayersOfMatch>>
    fun jointedMatch(token: String, matchPlayerId: String): Flow<Events<JointedMatchX>>
    fun getAllJointedMatch(token: String): Flow<Events<AllJoinedMatchModel>>
    fun getMatch(token: String, matchId: String): Flow<Events<MatchModelReponse>>

    fun getMatches(token: String): Flow<Events<List<MatchModelReponse>>>

    fun acceptMatch(token: String, playerMatchId: String): Flow<Events<AcceptResponse>>
    fun refuseMatch(token: String, playerMatchId: String): Flow<Events<RefuseModel>>
    fun getAllTerrain(token: String): Flow<Events<List<TerrrainModel>>>
}