package com.example.projetandroid.ui_layer.viewModels.user_viewModels

import android.app.DatePickerDialog
import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events.ErrorEvent
import com.example.projetandroid.Events.LoadingEvent
import com.example.projetandroid.Events.SuccessEvent
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.model.match.createModel.CreateMatchModelRequest
import com.example.projetandroid.model.match.matchModel.MatchModelReponse
import com.example.projetandroid.model.terrrain.TerrrainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class AddNowMatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val sharedPref: ShardPref
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState

    private val _terrainModel = mutableStateOf<List<TerrrainModel>?>(null)
    val terrrainModel: State<List<TerrrainModel>?> = _terrainModel

    var selectedIndex by mutableIntStateOf(-1)
    var date by mutableStateOf<LocalDate?>(null)
    var time by mutableStateOf<LocalTime?>(null)


    fun restUiState() {
        _uiState.value = UiState.Idle
    }

    fun loadTerrains() {

        matchRepository.getAllTerrain(sharedPref.getToken()).onEach {
            when (it) {
                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }

                is SuccessEvent -> {
                    _uiState.emit(UiState.Idle)
                    _terrainModel.value = it.data
                }
            }
        }
            .launchIn(viewModelScope)
    }

    init {
        loadTerrains()
    }

    fun addMatch() {

        if (date == null) {
            _uiState.value =
                UiState.Error("Date or time not specified, click on the icons to select")
            return

        }
        // mother fucker!!
        if (time == null) {
            _uiState.value =
                UiState.Error("Date or time not specified, click on the icons to select")
            return
        }
        if (selectedIndex == -1) {
            _uiState.value =
                UiState.Error("You Haven't Select Soccer Field")
            return
        }


        matchRepository.createMatch(
            sharedPref.getToken(),
            CreateMatchModelRequest(
                LocalDateTime.of(date, time).toString(),
                _terrainModel.value!![selectedIndex]._id
            )
        ).onEach {
            when (it) {
                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }

                is SuccessEvent -> {
                    _uiState.emit(UiState.Success("match created Successfully"))
                }
            }
        }.catch {
            _uiState.emit(
                UiState.Error(
                    message = it.localizedMessage ?: "unexpected error just happened"
                )
            )
        }
            .launchIn(viewModelScope)

    }

}