package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Dismiss
import com.example.projetandroid.Events.ErrorEvent
import com.example.projetandroid.Events.LoadingEvent
import com.example.projetandroid.Events.SuccessEvent
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.model.match.matchModel.MatchModelReponse
import com.example.projetandroid.ui_layer.event.EventBusType
import com.example.projetandroid.ui_layer.event.EventsBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val shardPref: ShardPref,
    private val eventBus: EventsBus
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState

    private val _matches = mutableStateListOf<MatchModelReponse>()
    val matches_: SnapshotStateList<MatchModelReponse> = _matches

    private fun loadAllMatches() {
        matchRepository.getMatches(shardPref.getToken()).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }
// 674762e2223251bfb7243490
                is ErrorEvent -> {
                    _uiState.emit(
                        UiState.Error(message = it.error, dismiss = Dismiss.DismissState(
                            dismiss = {
                                _uiState.value = UiState.Idle
                            }
                        ))
                    )
                }

                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _matches.addAll(it.data)
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
        viewModelScope.launch {
            eventBus.sharedFlowTrigger.collectLatest {
                it?.forEach { (key, value) ->

                    when (key) {
                        EventBusType.ADD_MATCH_EVENT -> {
                            if (value is MatchModelReponse) {
                                _matches.add(0, value)
                                println("Here we go we've add match")
                            }
                        }

                        else -> {}
                    }

                }
            }
        }
    }


    fun joinMatch(matchId: String) {
        matchRepository.joinMatch(shardPref.getToken(), matchId).onEach {

            when (it) {
                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(
                        message = it.error,
                        dismiss = Dismiss.DismissState {
                            _uiState.value = UiState.Idle
                        }
                    ))
                }

                is SuccessEvent -> {
                    eventBus.matchAddedEvent(
                        mapOf(EventBusType.JOIN_MATCH_EVENT to it.data._id)
                    )
                    _uiState.emit(UiState.Success(
                        message = "Match Joined Successfully",
                        dismiss = Dismiss.DismissState {
                            _uiState.value = UiState.Idle
                        }
                    ))
                }
            }
        }
            .catch {
                _uiState.emit(
                    UiState.Error(
                        message = it.localizedMessage ?: "unexpected error just happened",
                        dismiss = Dismiss.DismissState {
                            _uiState.value = UiState.Idle
                        }
                    )
                )
            }.launchIn(viewModelScope)
    }


}