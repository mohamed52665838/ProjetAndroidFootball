package com.example.projetandroid.ui_layer.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.Fields
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.ui_layer.shared.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                    shardPref.putToken(it.data.accessToken, it.data.refreshToken)
                    _state.value = ScreenState(data = it.data)
                }

                is Events.LoadingEvent -> {
                    _state.value = ScreenState(isLoading = true)
                }

                else -> {}
            }
        }
            .catch {
                _state.value = ScreenState(errorMessage = it.localizedMessage ?: "unexpected error")
            }
            .launchIn(viewModelScope)
    }

    // this one is dangerous please to let me down
    fun clearState() {
        _state.value = ScreenState()
    }
}