package com.example.projetandroid.model.match.joinMatch

data class MatchId(
    val __v: Int,
    val _id: String,
    val date: String,
    val playersOfMatch: List<String>,
    val terrainId: String,
    val userId: String
)