package com.example.projetandroid.model.match.createModel

data class CreateMatchModelResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val date: String,
    val playersOfMatch: List<Any>,
    val terrainId: String,
    val updatedAt: String,
    val userId: String
)