package com.example.projetandroid.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    val active: Boolean,
    val email: String,
    @SerializedName("last_name")
    val lastName: String? = null,
    val name: String,
    val phone: String?,
    val role: String
)