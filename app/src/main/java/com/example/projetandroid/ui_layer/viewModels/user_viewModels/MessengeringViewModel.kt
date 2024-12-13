package com.example.projetandroid.ui_layer.viewModels.user_viewModels

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.withFrameNanos
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.MessagePayload
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.ui_layer.event.SocketWrapperClass
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoadingDataError(
    val errorMessage: String,
    val onDismissError: () -> Unit
)

sealed class ServerStatus {
    data object Reconnecting : ServerStatus()
    data object Connected : ServerStatus()
    data object ConnectingError : ServerStatus()
    data object Init : ServerStatus()
}


// websocket
//


@HiltViewModel(assistedFactory = ChatRoomViewModel.ChatRoomFactory::class)
class ChatRoomViewModel @AssistedInject constructor(
    @Assisted val roomId: String,
    val matchRepository: MatchRepository,
    shardPref: ShardPref
) : ViewModel() {


    private val _listOfMessagePayload = mutableStateListOf<MessagePayload>()
    val listOfMessagePayload: SnapshotStateList<MessagePayload> = _listOfMessagePayload

    private val _matchesLoading = mutableStateOf(false)
    val matchesLoading: State<Boolean> = _matchesLoading

    private val _serverStatus = mutableStateOf<ServerStatus>(ServerStatus.Init)
    val serverStatus: State<ServerStatus> = _serverStatus

    private val _errorChannel = MutableStateFlow<LoadingDataError?>(null)
    val errorChannel: SharedFlow<LoadingDataError?> = _errorChannel

    val lazyListState = LazyListState()
    var messageData by mutableStateOf("")


    fun onMessageDataChange(newValue: String) {
        messageData = newValue
    }


    @AssistedFactory
    interface ChatRoomFactory {
        fun create(roomId: String): ChatRoomViewModel
    }

    val socketWrapperClass =
        SocketWrapperClass(shardPref, roomId, _serverStatus) {
            _listOfMessagePayload.add(it)
            lazyListState.requestScrollToItem(_listOfMessagePayload.lastIndex)
        }

    init {
        getAllMatchesX()
    }

    fun getAllMatchesX() {
        matchRepository.getAllMessages(roomId).onEach {
            when (it) {
                is Events.LoadingEvent -> {
                    _matchesLoading.value = true
                }

                is Events.SuccessEvent -> {
                    _matchesLoading.value = false
                    _listOfMessagePayload.addAll(it.data.map { it.toMessagePayload() })

                    if (_listOfMessagePayload.size > 1)
                        lazyListState.requestScrollToItem(_listOfMessagePayload.lastIndex)
                }

                is Events.ErrorEvent -> {
                    _matchesLoading.value = false
                    _errorChannel.emit(
                        LoadingDataError(
                            errorMessage = it.error,
                            onDismissError = { _errorChannel.value = null })
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun sendMessage() {
        socketWrapperClass.sendMessage(messageData)
        messageData = ""
    }


    override fun onCleared() {
        socketWrapperClass.disconnect()
        super.onCleared()
    }

}