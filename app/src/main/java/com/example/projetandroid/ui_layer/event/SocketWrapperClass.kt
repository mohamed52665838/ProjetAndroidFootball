package com.example.projetandroid.ui_layer.event

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.projetandroid.BASE_URL
import com.example.projetandroid.MessagePayload
import com.example.projetandroid.ShardPref
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.ServerStatus
import com.google.gson.Gson
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.IO.Options
import io.socket.client.Manager
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SocketWrapperClass(
    shardPref: ShardPref,
    roomId: String,
    serverStatus: MutableState<ServerStatus>,
    onItemAddedToList: (MessagePayload) -> Unit
) {

    private fun deserializeMessagePayload(jsonObject: Any): MessagePayload {
        // each call create instance !
        return Gson().fromJson(jsonObject.toString(), MessagePayload::class.java)
    }


    private val socket = IO.socket(
        BASE_URL,
        Options.builder().setExtraHeaders(mapOf("authorization" to listOf(shardPref.getToken())))
            .setQuery("roomId=${roomId}")
            .build()
    )

    init {

        socket.on(Socket.EVENT_DISCONNECT) {
            println("We Are Disconnected from the server")
        }

        socket.io().on(Manager.EVENT_RECONNECT_ATTEMPT) {
            serverStatus.value = ServerStatus.Reconnecting
            println("reconnecting....")
        }

        socket.on("response") {
            val currentValue = deserializeMessagePayload(it[0])
            currentValue.datetime_ =
                LocalDateTime.parse(currentValue.datetime_.split(".").first()).format(
                    DateTimeFormatter.ofPattern("hh:mm a")
                )
            onItemAddedToList(currentValue)
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) {
            serverStatus.value = ServerStatus.ConnectingError
            println("Connection Error")
        }
        socket.on(Socket.EVENT_CONNECT) {
            println("We Are Connected")
            serverStatus.value = ServerStatus.Connected
        }

        socket.connect()

    }


    fun isConnected(): Boolean {
        return socket.connected()
    }


    fun sendMessage(message: String) {
        socket.emit("message", message, Ack { returned ->
            println("we have return from server ${returned[0]}")
        })
    }


    fun disconnect() {
        socket.disconnect()
        socket.off("response")
    }


}