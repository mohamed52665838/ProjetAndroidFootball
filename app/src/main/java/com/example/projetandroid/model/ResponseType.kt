package com.example.projetandroid.model

data class ResponseType<T>(
    val data: T?,
    val status: Boolean,
    val message: String?,
    val error: List<String>?
)
