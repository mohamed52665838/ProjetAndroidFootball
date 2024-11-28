package com.example.projetandroid.ui_layer.viewModels.user_viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.data_layer.repository.MatchRepositoryStandards
import com.example.projetandroid.model.match.joinMatch.JointedMatchX
import com.example.projetandroid.model.match.ownMatchesModel.OwnMatch
import com.example.projetandroid.model.match.ownMatchesModel.OwnMatchsModel
import com.example.projetandroid.ui_layer.presentation.shared_components.Match
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchJointedCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeUserViewModelProtocol {
    fun getJointedList()
    fun getMyOwnMatches()
    fun restUIState()
}

abstract class HomeUserViewModelBase : ViewModel(), HomeUserViewModelProtocol {
    protected val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState

    protected val _jointedMatchX = mutableStateListOf<JointedMatchX>()
    val jointedMatchX: SnapshotStateList<JointedMatchX> = _jointedMatchX

    protected val _myOwnMatches = mutableStateListOf<OwnMatch>()
    val myOwnMatches: SnapshotStateList<OwnMatch> = _myOwnMatches

    override fun restUIState() {
        _uiState.value = UiState.Idle
    }
}

@HiltViewModel
class HomeUserViewModel @Inject constructor(
    private val sharedPref: ShardPref,
    private val matchRepository: MatchRepository
) : HomeUserViewModelBase() {


    init {
        getMyOwnMatches()
        getJointedList()
    }

    override fun getJointedList() {
        matchRepository.getAllJointedMatch(sharedPref.getToken()).onEach {

            when (it) {
                is Events.LoadingEvent -> {
                    // no loading state
                }

                is Events.ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }

                is Events.SuccessEvent -> {
                    _uiState.emit(UiState.Success(message = "your data updated successfully"))
                    _jointedMatchX.addAll(it.data.jointedMatch)
                }
            }


        }.launchIn(viewModelScope)
    }

    override fun getMyOwnMatches() {
        matchRepository.getMyOwnMatches(sharedPref.getToken()).onEach {

            when (it) {
                is Events.LoadingEvent -> {
                    // no loading state
                }

                is Events.ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }

                is Events.SuccessEvent -> {
                    _uiState.emit(UiState.Success(message = "your data updated successfully"))
                    _myOwnMatches.addAll(it.data.ownMatchs)
                }
            }


        }.launchIn(viewModelScope)
    }


}


class HomeUserViewModelPreview : HomeUserViewModelBase() {


    override fun getJointedList() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading())
            delay(1000)
            _uiState.emit(UiState.Error(message = "Error unexpected"))
        }
    }

    override fun getMyOwnMatches() {
        TODO("Not yet implemented")
    }
}
