package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Dashboard
import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


interface OTACodeViewModelProtocol {
    fun sendOTA()
    fun verifyOTP()
    fun resetUiState()
}

abstract class OTACodeViewModelBase : ViewModel(), OTACodeViewModelProtocol {
    protected val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState
    var codeOTA by mutableStateOf("")
    var canSubmit by mutableStateOf(false)
    var canCancel by mutableStateOf(true)
    var errorMessage by mutableStateOf<String?>(null)


    override fun resetUiState() {
        _uiState.value = UiState.Idle
    }
}


@HiltViewModel(assistedFactory = OTACodeViewModel.OTACodeViewModelFactory::class)
class OTACodeViewModel @AssistedInject constructor(
    @Assisted private val email: String,
    private val userRepository: UserRepository,
    private val shardPref: ShardPref
) : OTACodeViewModelBase() {

    @AssistedFactory
    interface OTACodeViewModelFactory {
        fun create(email: String): OTACodeViewModel
    }

    init {
        sendOTA()
    }


    override fun sendOTA() {
        userRepository.sendOtp(email).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                    canSubmit = true
                    _uiState.emit(UiState.Error(message = "same things went wrong, resent please"))
                }

                is Events.SuccessEvent -> {
                    canSubmit = true
                }

                is Events.LoadingEvent -> {
                    // no loading state in send otp
                }
            }
        }.catch {
            _uiState.emit(
                UiState.Error(
                    message = it.localizedMessage ?: "same things went wrong, sorry"
                )
            )
        }
            .launchIn(viewModelScope)
    }

    override fun verifyOTP() {
        userRepository.verifyOtp(email, codeOTA).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                    canSubmit = true
                    canCancel = true
                    _uiState.emit(UiState.Error(message = it.error))
                }

                is Events.SuccessEvent -> {
                    _uiState.emit(UiState.NavigateEvent(message = "welcome, are you ready!") {
                        navigate(Dashboard) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    })
                }

                is Events.LoadingEvent -> {
                    canSubmit = false
                    canCancel = false
                    // no loading state in send otp
                    _uiState.emit(UiState.Loading(message = "Verifying OTP Code..."))
                }
            }
        }.catch {
            _uiState.emit(
                UiState.Error(
                    message = it.localizedMessage ?: "same things went wrong, sorry"
                )
            )
        }
            .launchIn(viewModelScope)
    }

}


class OTACodeViewModelPreview : OTACodeViewModelBase() {
    override fun sendOTA() {
        errorMessage = null
        if (!codeOTA.isDigitsOnly()) {
            errorMessage = "code OTA is Digits Only"
            return
        }
        viewModelScope.launch {
            _uiState.emit(UiState.Loading("Sending OTA code..."))
            canSubmit = false
            canCancel = false
            delay(1000)
            _uiState.emit(UiState.Success(message = "Success State"))
            canSubmit = true
            canCancel = true
        }
    }

    override fun verifyOTP() {
        TODO("Not yet implemented")
    }
}



