package com.example.projetandroid.model.match.joinMatch

data class AcceptResponse(
    val _id: String,
    val isAccepted: Boolean,
    val matchId: MatchIdX,
    val userId: String
)