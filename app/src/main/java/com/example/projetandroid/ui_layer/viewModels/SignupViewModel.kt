package com.example.projetandroid.ui_layer.viewModels

import android.media.metrics.Event
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
import com.example.projetandroid.SignUpFragments
import com.example.projetandroid.SignupFields
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.data_layer.repository.UserRepositoryStandards
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.ui_layer.shard.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val userRepository: UserRepositoryStandards,
    val shardPref: ShardPref
) : ViewModel() {


    // Second Screen Fields
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirmation by mutableStateOf("")

    // First Screen Fields
    var name by mutableStateOf("")
    var lastname by mutableStateOf("")
    var phoneNumber by mutableStateOf("")


    private var _errorMap = mutableStateMapOf<SignupFields, String>()
    val errorMap: SnapshotStateMap<SignupFields, String> = _errorMap

    private val _currentScreenState = mutableStateOf(ScreenState<TokenModel>())
    val currentScreenState: State<ScreenState<TokenModel>> = _currentScreenState

    private val _currentScreen = mutableStateOf(SignUpFragments.FIRST_FRAGMENT)
    val currentScreen: State<SignUpFragments> = _currentScreen

    fun clearSecondScreen() {
        _errorMap.clear()
        email = ""
        password = ""
        passwordConfirmation = ""
    }


    fun clearFistScreen() {
        _errorMap.clear()
        name = ""
        lastname = ""
        phoneNumber = ""
    }

    fun clearNetworkError() {
        _currentScreenState.value = ScreenState()
    }


    fun validateFirstScreen(): Boolean {
        _errorMap.clear()
        if (name.isNotEmpty() && lastname.isNotEmpty() && phoneNumber.length == 8) {
            _currentScreen.value = SignUpFragments.SECOND_FRAGMENT
            return true
        }
        return false
    }


    fun validateSecondScreen(): Boolean {
        _errorMap.clear()
        // validation
        // email
        val regularExpressionEmail =
            """^[a-zA-Z]{1}[a-zA-Z0-9_\.-]*[a-zA-Z0-9]@[a-zA-Z]{1}[a-zA-Z-]*[a-zA-Z]{1}(\.[a-zA-Z]{2,})+$""".toRegex()

        if (!regularExpressionEmail.matches(email)) {
            _errorMap[SignupFields.EMAIL] = "invalid email, please check your email"
            println("we got error of email")
        }
        if (password.length < 6) {
            _errorMap[SignupFields.PASSWORD] = "password at least 6 character"
            println("we got error of password")
        }

        if (_errorMap.isNotEmpty()) {
            return false
        }
        return true
    }


    fun backFirstScreen(): Boolean {
        clearSecondScreen()
        _currentScreen.value = SignUpFragments.FIRST_FRAGMENT
        return true
    }

    fun submit() {
        if (!validateSecondScreen()) return
        userRepository.signup(name, lastname, password, email, phoneNumber).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                    _currentScreenState.value = ScreenState(errorMessage = it.error)
                }

                is Events.SuccessEvent -> {
                    userRepository.sendOtp(email).onEach { message ->
                        when (message) {
                            is Events.ErrorEvent -> {
                                _currentScreenState.value = ScreenState(errorMessage = it.error)
                            }

                            is Events.LoadingEvent -> {
                                _currentScreenState.value =
                                    ScreenState(data = null, isLoading = true)
                            }

                            is Events.SuccessEvent -> {
                                it.data!!
                                shardPref.putToken(it.data.accessToken, it.data.refreshToken)
                                _currentScreenState.value = ScreenState(data = it.data)
                            }

                            else -> {}
                        }
                    }.launchIn(viewModelScope)

                }

                is Events.LoadingEvent -> {
                    _currentScreenState.value = ScreenState(isLoading = true)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)

    }
}