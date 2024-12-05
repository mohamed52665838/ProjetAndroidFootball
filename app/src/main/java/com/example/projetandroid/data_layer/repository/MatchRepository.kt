package com.example.projetandroid.data_layer.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
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
    private val matchAPI: MatchAPI,
    private val pref: ShardPref
) : MatchRepositoryStandards {


    override fun createMatch(
        createMatchModelRequest: CreateMatchModelRequest
    ): Flow<Events<MatchModelReponse>> = runRequest(
        mapError = mapOf(
            422 to "wrong data within the body, rapport issue",
            400 to "wrong data within the body, rapport issue"
        ),
        pref
    ) { token ->
        matchAPI.createMatch(token, createMatchModelRequest)
    }

    override fun joinMatch(matchId: String): Flow<Events<PlayersOfMatch>> =
        runRequest(
            mapError = mapOf(
                422 to "Key not valid, please check your key",
                404 to "Match not exists",
                409 to "You're already joined this match",
                400 to "Match full with players"
            ),
            pref
        ) {
            matchAPI.join(it, matchId)
        }

    override fun jointedMatch(matchPlayerId: String): Flow<Events<JointedMatchX>> =
        runRequest(
            pref = pref
        ) {
            matchAPI.jointed(it, matchPlayerId)
        }// 675021274b0c5c803fc55316


    override fun getAllJointedMatch(): Flow<Events<AllJoinedMatchModel>> = runRequest(pref = pref) {
        matchAPI.getAllJointedMatch(it)
    }


    override fun getMyOwnMatches(): Flow<Events<OwnMatchsModel>> = runRequest(pref = pref) {
        matchAPI.getMyOwnMatches(it)
    }


    override fun getMatch(matchId: String): Flow<Events<MatchModelReponse>> =
        runRequest(pref = pref) {
            matchAPI.getMatch(it, matchId)
        }


    override fun getMatches(): Flow<Events<List<MatchModelReponse>>> = runRequest(pref = pref) {
        matchAPI.matches(it)
    }


    override fun acceptMatch(playerMatchId: String): Flow<Events<AcceptResponse>> =
        runRequest(pref = pref) {
            matchAPI.accepteUser(it, playerMatchId)
        }

    override fun refuseMatch(playerMatchId: String): Flow<Events<RefuseModel>> =
        runRequest(pref = pref) {
            matchAPI.refuseUser(it, playerMatchId)
        }


    override fun getAllTerrain(): Flow<Events<List<TerrrainModel>>> = runRequest(pref = pref) {
        matchAPI.getAllTerrain(it)
    }


}