package com.example.projetandroid.data_layer.network.api

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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface MatchAPI {

    @GET("/user/matches")
    suspend fun getMyOwnMatches(@Header("Authorization") token: String): Response<ResponseType<OwnMatchsModel?>?>

    @POST("/match")
    suspend fun createMatch(
        @Header("Authorization") token: String,
        @Body createMatchModelRequest: CreateMatchModelRequest
    ): Response<ResponseType<MatchModelReponse?>?>


    @GET("/match/{match}")
    suspend fun getMatch(
        @Header("Authorization") token: String,
        @Path("match") matchModelId: String
    ): Response<ResponseType<MatchModelReponse?>?>

    @GET("/user/match/joined")
    suspend fun getAllJointedMatch(@Header("Authorization") token: String): Response<ResponseType<AllJoinedMatchModel?>?>


    @PUT("/match-player/accept/{matchPlayerId}")
    suspend fun accepteUser(
        @Header("Authorization") token: String,
        @Path("matchPlayerId") playerUser: String
    ): Response<ResponseType<AcceptResponse?>?>


    // protocol://host/endpoint?query=fafafa&keyword2=fafafaer


    @PUT("/match-player/refuse/{matchPlayerId}")
    suspend fun refuseUser(
        @Header("Authorization") token: String,
        @Path("matchPlayerId") playerUser: String
    ): Response<ResponseType<RefuseModel?>?>


    @GET("/match")
    suspend fun matches(
        @Header("Authorization") token: String,
    ): Response<ResponseType<List<MatchModelReponse>?>?>


    @POST("/match-player/join/{matchId}")
    suspend fun join(
        @Header("Authorization") token: String,
        @Path("matchId") matchId: String
    ): Response<ResponseType<PlayersOfMatch?>?>

    @GET("/match-player/{matchPlayerId}")
    suspend fun jointed(
        @Header("Authorization") token: String,
        @Path("matchPlayerId") matchId: String
    ): Response<ResponseType<JointedMatchX?>?>

    @GET("/terrain")
    suspend fun getAllTerrain(
        @Header("Authorization") token: String,
    ): Response<ResponseType<List<TerrrainModel>?>?>

}