package com.example.projetandroid.ui_layer.viewModels.manager_viewModels

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.ChatWithStewieMessage
import com.example.projetandroid.Dismiss
import com.example.projetandroid.Events
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.ChatWithStewieRepository
import com.example.projetandroid.model.chat_with_stewie.ChatWithStewieModelSendMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChatWithStewieViewModel @Inject constructor(
    private val chatWithStewieRepository: ChatWithStewieRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _listOfMessages = mutableStateListOf<ChatWithStewieMessage>()
    val listOfMessages: SnapshotStateList<ChatWithStewieMessage> = _listOfMessages

    val lazyListState = LazyListState()

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState: SharedFlow<UiState> = _uiState

    var messageContent by mutableStateOf("")

    fun sendMessage() {
        if (messageContent.isEmpty())
            return
        val currentTime = generateCurrentTime()
        _listOfMessages.add(
            ChatWithStewieMessage(
                content = messageContent,
                time = currentTime,
                isItMine = true
            )
        )
        lazyListState.requestScrollToItem(_listOfMessages.lastIndex)

        chatWithStewieRepository.sendMessage(ChatWithStewieModelSendMessage(content = messageContent))
            .onEach {
                when (it) {
                    is Events.LoadingEvent -> {
                        _uiState.emit(UiState.Loading())
                    }

                    is Events.SuccessEvent -> {
                        _uiState.emit(UiState.Idle)
                        _listOfMessages.add(
                            ChatWithStewieMessage(
                                content = it.data.parts.first().text,
                                time = currentTime,
                                isItMine = false
                            )
                        )
                        lazyListState.requestScrollToItem(_listOfMessages.lastIndex)
                    }

                    is Events.ErrorEvent -> {
                        _uiState.emit(
                            UiState.Error(
                                it.error,
                                Dismiss.DismissState { _uiState.tryEmit(UiState.Idle) }
                            )
                        )
                    }
                }
            }
            .catch {
                _uiState.emit(
                    UiState.Error(
                        it.localizedMessage ?: "unexpected error just happened",
                        Dismiss.DismissState { _uiState.tryEmit(UiState.Idle) }
                    )
                )
            }.launchIn(viewModelScope)


    }

    fun onMessageChange(value: String) {
        messageContent = value
    }

    private fun generateCurrentTime(): String {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))
    }


    fun clearText() {
        messageContent = ""
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle["key"] = 24

    }

}