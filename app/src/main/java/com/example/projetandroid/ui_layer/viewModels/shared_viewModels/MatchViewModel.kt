package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Dismiss
import com.example.projetandroid.Events.ErrorEvent
import com.example.projetandroid.Events.SuccessEvent
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.model.match.joinMatch.JointedMatchX
import com.example.projetandroid.model.match.matchModel.MatchModelReponse
import com.example.projetandroid.model.match.matchModel.PlayersOfMatch
import com.example.projetandroid.ui_layer.event.EventBusType
import com.example.projetandroid.ui_layer.event.EventsBus
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.example.projetandroid.Events.LoadingEvent as LoadingEvent


@HiltViewModel(assistedFactory = MatchViewModel.MatchViewModelFactory::class)
class MatchViewModel @AssistedInject constructor(
    private val matchRepository: MatchRepository,
    private val eventBus: EventsBus,
    @Assisted private val matchId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState

    private val _matchReponseModel = mutableStateOf<MatchModelReponse?>(null)
    val matchReponseModel: State<MatchModelReponse?> = _matchReponseModel

    private val _playersOfMatch = mutableStateListOf<PlayersOfMatch>()
    val playersOfMatch: SnapshotStateList<PlayersOfMatch> = _playersOfMatch

    @AssistedFactory
    interface MatchViewModelFactory {
        fun create(matchId: String): MatchViewModel
    }

    init {
        getMatch()
    }

    fun removeState() {
        _uiState.value = UiState.Idle
    }

    fun getMatch() {
        matchRepository.getMatch(matchId).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }


                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _matchReponseModel.value = it.data
                    _playersOfMatch.addAll(it.data.playersOfMatch.toList())
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


    fun accpetUser(idUser: String) {
        matchRepository.acceptMatch(idUser).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(
                        message = it.error,
                        dismiss = Dismiss.DismissState {
                            removeState()
                        }
                    ))
                }


                is SuccessEvent -> {
                    val acceptedPlayer =
                        _playersOfMatch.first { playersOfMatch -> it.data._id == playersOfMatch._id }
                    val accptedUserCopy = acceptedPlayer.copy(isAccepted = true)
                    _playersOfMatch.remove(acceptedPlayer)
                    _playersOfMatch.add(accptedUserCopy)
                    _uiState.emit(UiState.Idle)
                }
            }
        }
            .catch {
                _uiState.emit(
                    UiState.Error(
                        message = it.localizedMessage ?: "unexpected error just happened",
                        dismiss = Dismiss.DismissState {
                            removeState()
                        }
                    )
                )
            }.launchIn(viewModelScope)
    }

    fun refuseUser(idUser: String) {
        matchRepository.refuseMatch(idUser).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(
                        message = it.error,
                        dismiss = Dismiss.DismissState {
                            removeState()
                        }
                    ))
                }


                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _playersOfMatch.removeIf { it._id == idUser }
                }
            }
        }
            .catch {
                _uiState.emit(
                    UiState.Error(
                        message = it.localizedMessage ?: "unexpected error just happened",
                        dismiss = Dismiss.DismissState {
                            removeState()
                        }
                    )
                )
            }.launchIn(viewModelScope)
    }


    fun joinMatch(matchId: String) {
        matchRepository.joinMatch(matchId).onEach {

            when (it) {
                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(
                        message = it.error,
                        dismiss = Dismiss.DismissState {
                            removeState()
                        }
                    ))
                }

// 674fa4762f8ac64f1cca1531
                is SuccessEvent -> {
                    eventBus.matchAddedEvent(mapOf(EventBusType.JOIN_MATCH_EVENT to it.data._id))
                    _playersOfMatch.add(it.data)
                    _uiState.emit(UiState.Idle)
                }
            }
        }
            .catch {
                _uiState.emit(
                    UiState.Error(
                        message = it.localizedMessage ?: "unexpected error just happened",
                        dismiss = Dismiss.DismissState {
                            removeState()
                        }
                    )
                )
            }.launchIn(viewModelScope)
    }

    override fun onCleared() {

        super.onCleared()
    }
}