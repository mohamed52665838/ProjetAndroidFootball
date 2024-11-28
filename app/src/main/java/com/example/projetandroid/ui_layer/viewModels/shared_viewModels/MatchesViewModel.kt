package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events.ErrorEvent
import com.example.projetandroid.Events.LoadingEvent
import com.example.projetandroid.Events.SuccessEvent
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.model.match.matchModel.MatchModelReponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val shardPref: ShardPref
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState

    private val _matches = mutableStateOf<List<MatchModelReponse>?>(null)
    val matches_: State<List<MatchModelReponse>?> = _matches


    fun loadAllMatches() {
        matchRepository.getMatches(shardPref.getToken()).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }


                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _matches.value = it.data
                }
            }
        }
            .catch {
                _uiState.emit(
                    UiState.Error(
                        message = it.localizedMessage ?: "unexpected error just happened"
                    )
                )
            }.launchIn(viewModelScope)
    }


    init {
        loadAllMatches()
    }

}