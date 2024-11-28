package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events.ErrorEvent
import com.example.projetandroid.Events.SuccessEvent
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.model.match.matchModel.MatchModelReponse
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
    private val sharedPref: ShardPref,
    @Assisted private val matchId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState

    private val _matchReponseModel = mutableStateOf<MatchModelReponse?>(null)
    val matchReponseModel: State<MatchModelReponse?> = _matchReponseModel

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
        matchRepository.getMatch(sharedPref.getToken(), matchId).onEach {
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
        matchRepository.acceptMatch(sharedPref.getToken(), idUser).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }


                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    var currentSelected =
                        _matchReponseModel.value?.playersOfMatch?.first { playersOfMatch -> it.data._id == playersOfMatch._id }
                    _matchReponseModel.value?.playersOfMatch?.remove(currentSelected)
                    currentSelected?.isAccepted = true

                    if (currentSelected != null) {
                        _matchReponseModel.value?.playersOfMatch?.add(currentSelected)
                    }
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

    fun refuseUser(idUser: String) {
        matchRepository.refuseMatch(sharedPref.getToken(), idUser).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }


                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _matchReponseModel.value?.playersOfMatch?.removeIf { it._id == idUser }
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


    fun joinMatch(matchId: String) {
        matchRepository.joinMatch(sharedPref.getToken(), matchId).onEach {
            when (it) {
                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }


                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _matchReponseModel.value?.playersOfMatch?.add(it.data)
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
}