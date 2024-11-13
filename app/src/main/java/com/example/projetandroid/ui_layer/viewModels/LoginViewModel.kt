package com.example.projetandroid.ui_layer.viewModels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.Fields
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.ui_layer.shard.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val shardPref: ShardPref
) : ViewModel() {

    // email, password
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _state = mutableStateOf(ScreenState<TokenModel>(data = null))
    val state: State<ScreenState<TokenModel>> = _state

    private var _errorMap = mutableStateMapOf<Fields, String>()
    val errorMap: SnapshotStateMap<Fields, String> = _errorMap

    fun clearInput() {
        _errorMap.clear()
        email = ""
        password = ""
    }

    fun login() {
        _errorMap.clear()
        // validation
        // email
        val regularExpressionEmail =
            """^[a-zA-Z]{1}[a-zA-Z0-9_\.-]*[a-zA-Z0-9]@[a-zA-Z]{1}[a-zA-Z-]*[a-zA-Z]{1}(\.[a-zA-Z]{2,})+$""".toRegex()

        if (!regularExpressionEmail.matches(email)) {
            _errorMap[Fields.EMAIL] = "invalid email, please check your email"
            println("we got error of email")
        }

        if (password.length < 6) {
            _errorMap[Fields.PASSWORD] = "password at least 6 character"
            println("we got error of password")
        }

        if (_errorMap.isNotEmpty()) {
            return
        }

        userRepository.signin(email, password).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                    _state.value = ScreenState(errorMessage = it.error)
                }

                is Events.SuccessEvent -> {
                    _state.value = ScreenState(data = it.data)
                    shardPref.putToken(it.data.accessToken, it.data.refreshToken)
                }

                is Events.LoadingEvent -> {
                    _state.value = ScreenState(isLoading = true)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    // this one is dangerous please to let me down
    fun clearState() {
        _state.value = ScreenState()
    }
}