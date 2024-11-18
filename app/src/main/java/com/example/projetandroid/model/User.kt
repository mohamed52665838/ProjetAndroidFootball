package com.example.projetandroid.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    val active: Boolean,
    val email: String,
    @SerializedName("last_name")
    val lastName: String,
    val name: String,
    val password: String,
    val phone: String
)