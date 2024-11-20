package com.example.projetandroid.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class AddressSearchJson(
    val addresstype: String,
    val boundingbox: List<String>,
    @SerializedName("class")
    val class_: String,
    val display_name: String,
    val importance: Double,
    val lat: String,
    val licence: String,
    val lon: String,
    val name: String,
    val osm_id: Long,
    val osm_type: String,
    val place_id: Long,
    val place_rank: Long,
    val type: String
)