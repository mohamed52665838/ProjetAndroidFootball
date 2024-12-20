package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.network.api.StewieAPI
import com.example.projetandroid.model.Message
import com.example.projetandroid.model.chat_with_stewie.ChatWithStewieModelSendMessage
import com.example.projetandroid.model.chat_with_stewie.StewieResponse
import com.example.projetandroid.runRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatWithStewieRepository @Inject constructor(
    val stewieAPI: StewieAPI,
    val shardPref: ShardPref
) : ChatWithStewieRepositoryStandards {

    override fun sendMessage(content: ChatWithStewieModelSendMessage): Flow<Events<StewieResponse>> =
        runRequest(pref = shardPref) {
            stewieAPI.sendMessage(it, content)
        }

    override fun destroySession(): Flow<Events<Message>> = runRequest(pref = shardPref) {
        stewieAPI.destroySession(it)
    }
}