package com.example.projetandroid.model.match.matchModel

data class MatchModelReponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val date: String,
    val playersOfMatch: List<PlayersOfMatch>,
    val terrainId: TerrainId,
    val updatedAt: String,
    val userId: UserIdX
)