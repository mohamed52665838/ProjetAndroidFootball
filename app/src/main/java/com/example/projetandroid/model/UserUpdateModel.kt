package com.example.projetandroid.model

import kotlinx.serialization.SerialName

data class UserUpdateModel(
    val name: String,
    @SerialName("last_name")
    val lastname: String,
    val phoneNumber: String
)
