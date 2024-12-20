package com.example.projetandroid.ui_layer.viewModels.manager_viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.SignIn
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.ChatWithStewieRepository
import com.example.projetandroid.data_layer.repository.SoccerFieldRepository
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.SoccerField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface HomeManagerViewModelProtocol {

    fun loadSoccerField()
    fun restUiState()
    fun loadStatistics() // matches of today, how much we made today

}


abstract class HomeManagerViewModelBase : ViewModel(), HomeManagerViewModelProtocol {


}

@HiltViewModel
class HomeManagerViewModel @Inject constructor(
    private val soccerFieldRepository: SoccerFieldRepository,
    private val chatWithStewieRepository: ChatWithStewieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateWatcher: SharedFlow<UiState> = uiState

    private val soccerField = mutableStateOf<SoccerField?>(null)
    val soccerFieldWatcher: State<SoccerField?> = soccerField

    private val _isLoadedData = mutableStateOf(false)
    val isLoadedData: State<Boolean> = _isLoadedData


    fun restUiState() {
        uiState.value = UiState.Idle
    }

    init {
        print("saved state handle message ${savedStateHandle.get<Int>("key")}")
        loadSoccerField()
    }

    fun loadSoccerField() {
        soccerFieldRepository.own().onEach {
            when (it) {
                is Events.LoadingEvent -> {
                    uiState.emit(UiState.Loading())
                }

                is Events.ErrorEvent -> {
                    _isLoadedData.value = true
                    uiState.emit(UiState.Error(message = it.error))
                }

                is Events.SuccessEvent -> {
                    _isLoadedData.value = true
                    soccerField.value = it.data
                    restUiState()
                }
            }
        }.catch {
            uiState.emit(
                UiState.Error(
                    message = it.localizedMessage ?: "unexpected error just happened"
                )
            )
        }.launchIn(viewModelScope)
    }


    fun destroySession() {
        chatWithStewieRepository.destroySession().onEach {
            when (it) {
                is Events.LoadingEvent -> {
                }

                is Events.ErrorEvent -> {
                    _isLoadedData.value = true
                    uiState.emit(UiState.Error(message = it.error))
                }

                is Events.SuccessEvent -> {

                }
            }
        }.catch {
            uiState.emit(
                UiState.Error(
                    message = it.localizedMessage ?: "unexpected error just happened"
                )
            )
        }.launchIn(viewModelScope)
    }


}