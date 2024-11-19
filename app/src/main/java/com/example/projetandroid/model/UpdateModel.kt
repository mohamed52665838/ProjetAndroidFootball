package com.example.projetandroid.model

import com.google.gson.annotations.SerializedName

data class UpdateModel(
    val name: String,
    val phone: String,
    @SerializedName("last_name")
    val lastName: String
)
