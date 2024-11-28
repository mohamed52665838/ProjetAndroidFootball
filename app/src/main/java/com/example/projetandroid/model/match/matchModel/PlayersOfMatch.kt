package com.example.projetandroid.model.match.matchModel

data class PlayersOfMatch(
    val _id: String,
    var isAccepted: Boolean,
    val matchId: String,
    val userId: UserId
)