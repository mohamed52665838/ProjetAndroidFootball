package com.example.projetandroid.model.terrrain

data class TerrainMatch(
    val __v: Int,
    val _id: String,
    val date: String,
    val playersOfMatch: List<PlayersOfMatch>,
    val terrainId: String,
    val userId: String
)