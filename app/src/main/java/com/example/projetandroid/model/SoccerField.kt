package com.example.projetandroid.model

data class SoccerField(
    val date: String?,
    val description: String?,
    val height: Double?,
    val label: String?,
    val latitude: String?,
    val longitude: String?,
    val address: String = "simple address",
    val price: Double?,
    val width: Double?,
    val matchsIn: List<String>? = null
)