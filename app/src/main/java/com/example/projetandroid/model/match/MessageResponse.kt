package com.example.projetandroid.model.match

import com.example.projetandroid.MessagePayload
import kotlinx.serialization.SerialName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MessageResponse(
    val userId: UserIdMessage,
    val content: String,
    val createdAt: String
) {
    fun toMessagePayload(): MessagePayload {
        return MessagePayload(
            userName = userId.name,
            userId = userId._id,
            content = content,
            LocalDateTime.parse(createdAt.split(".").first()).toLocalTime()
                .format(DateTimeFormatter.ofPattern("hh:mm a"))
        )
    }
}


data class UserIdMessage(
    val _id: String,
    val name: String
)