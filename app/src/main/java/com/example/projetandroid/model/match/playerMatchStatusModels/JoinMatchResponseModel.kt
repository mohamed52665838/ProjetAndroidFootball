package com.example.projetandroid.model.match.playerMatchStatusModels

data class JoinMatchResponseModel(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isAccepted: Boolean,
    val matchId: String,
    val updatedAt: String,
    val userId: String
)