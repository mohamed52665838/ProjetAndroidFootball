package com.example.projetandroid.model

data class ResponseType<T>(
    val data: T?,
    val status: Boolean,
    val error: String?,
    val message: String?
)
