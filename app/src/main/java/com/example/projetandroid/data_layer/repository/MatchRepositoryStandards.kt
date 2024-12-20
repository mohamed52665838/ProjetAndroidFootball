package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.match.MessageResponse
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

    fun getMyOwnMatches(): Flow<Events<OwnMatchsModel>>

    fun createMatch(
        createMatchModelRequest: CreateMatchModelRequest
    ): Flow<Events<MatchModelReponse>>

    fun getAllMessages(matchId: String): Flow<Events<List<MessageResponse>>>
    fun joinMatch(matchId: String): Flow<Events<PlayersOfMatch>>
    fun jointedMatch(matchPlayerId: String): Flow<Events<JointedMatchX>>
    fun getAllJointedMatch(): Flow<Events<AllJoinedMatchModel>>
    fun getMatch(matchId: String): Flow<Events<MatchModelReponse>>

    fun getMatches(): Flow<Events<List<MatchModelReponse>>>

    fun acceptMatch(playerMatchId: String): Flow<Events<AcceptResponse>>
    fun refuseMatch(playerMatchId: String): Flow<Events<RefuseModel>>
    fun getAllTerrain(): Flow<Events<List<TerrrainModel>>>

}