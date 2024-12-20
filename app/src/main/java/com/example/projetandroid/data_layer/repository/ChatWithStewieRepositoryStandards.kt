package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.model.Message
import com.example.projetandroid.model.chat_with_stewie.ChatWithStewieModelSendMessage
import com.example.projetandroid.model.chat_with_stewie.StewieResponse
import kotlinx.coroutines.flow.Flow

interface ChatWithStewieRepositoryStandards {

    fun sendMessage(content: ChatWithStewieModelSendMessage): Flow<Events<StewieResponse>>
    fun destroySession(): Flow<Events<Message>>
}