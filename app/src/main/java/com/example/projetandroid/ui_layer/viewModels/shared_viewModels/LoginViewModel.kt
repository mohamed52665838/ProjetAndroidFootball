package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.BundledTextField
import com.example.projetandroid.Dashboard
import com.example.projetandroid.Dismiss
import com.example.projetandroid.Events
import com.example.projetandroid.R
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


interface LoginViewModelProtocol {
    fun login()
    fun resetUiState()
    fun resetInput()
    fun clearInput()
}


abstract class LoginViewModelBase : ViewModel(), LoginViewModelProtocol {
    val fields = listOf(
        BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_account_circle_24,
            validator = """^[a-zA-Z][a-zA-Z0-9_.-]*[a-zA-Z0-9]@[a-zA-Z][a-zA-Z-]*[a-zA-Z](\.[a-zA-Z]{2,})+$""".toRegex(),
            onErrorMessage = mutableStateOf<String?>(null),
            supportErrorMessage = "please make sure it's value email",
            label = "email, g@go.com"
        ),
        BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_lock_24,
            onErrorMessage = mutableStateOf<String?>(null),
            validator = """.{6,}""".toRegex(),
            supportErrorMessage = "password must be at lease 8 characters",
            label = "password"
        )
    )
    protected val uiStateFlow = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateFlowWatcher: StateFlow<UiState> = uiStateFlow
    var inputEnabled by mutableStateOf(true)

    override fun resetInput() {
        fields.forEach {
            it.clearValidation()
        }
    }

    override fun resetUiState() {
        uiStateFlow.value = UiState.Idle
    }

    override fun clearInput() {

        fields.forEach {
            it.value.value = ""
        }
    }

}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val shardPref: ShardPref
) : LoginViewModelBase() {

    // single responsibility


    override fun login() {
        fields.forEach { it.clearValidation() }
        fields.filter { !it.validate() }.isNotEmpty().also {
            if (it) return
        }
        viewModelScope.launch {
            userRepository.signin(fields[0].value.value, fields[1].value.value).onEach {
                when (it) {
                    is Events.LoadingEvent -> {
                        uiStateFlow.emit(UiState.Loading(message = "sign in"))
                    }

                    is Events.ErrorEvent -> {
                        uiStateFlow.emit(
                            UiState.Error(
                                message = it.error,
                                dismiss = Dismiss.DismissState(dismiss = { resetUiState() })
                            )
                        )
                    }

                    is Events.SuccessEvent -> {
                        shardPref.putToken(it.data.accessToken, it.data.refreshToken)
                        uiStateFlow.emit(
                            UiState.NavigateEvent(
                                message = "welcome back",
                                navigation = {
                                    navigate(Dashboard) {
                                        popUpTo(0) {
                                            inclusive = true
                                        }
                                    }
                                    resetUiState()
                                },
                            )
                        )
                    }
                }
            }.catch {
                uiStateFlow.emit(
                    UiState.Error(
                        it.localizedMessage ?: "unexpected error just happened",
                        dismiss = Dismiss.DismissState(dismiss = { resetUiState() })
                    )
                )
            }.launchIn(viewModelScope)
        }
    }
}

class LoginViewModelPreview : LoginViewModelBase() {
    override fun login() {
        fields.forEach { it.clearValidation() }
        fields.filter { !it.validate() }.isNotEmpty().also {
            if (it)
                return
        }
        viewModelScope.launch {
            uiStateFlow.emit(UiState.Loading(message = "loading"))
            delay(1000)
            uiStateFlow.emit(UiState.Success(message = "State Success"))

        }

    }
}





