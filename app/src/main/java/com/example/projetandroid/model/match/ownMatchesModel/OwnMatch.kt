package com.example.projetandroid.model.match.ownMatchesModel

data class OwnMatch(
    val __v: Int,
    val _id: String,
    val date: String,
    val playersOfMatch: List<PlayersOfMatch>,
    val terrainId: TerrainId,
    val userId: String
)