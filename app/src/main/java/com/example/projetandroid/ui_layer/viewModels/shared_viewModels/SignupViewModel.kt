package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.BundledTextField
import com.example.projetandroid.CodeOTP
import com.example.projetandroid.Events
import com.example.projetandroid.R
import com.example.projetandroid.Role
import com.example.projetandroid.ShardPref
import com.example.projetandroid.SignUpFragments
import com.example.projetandroid.SignupFields
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.UserRepositoryStandards
import com.example.projetandroid.translateRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


interface SignUpViewModelProtocol {
    fun signUp()
}

abstract class SignUpViewModelBase : ViewModel(), SignUpViewModelProtocol {

    // uiState
    protected val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState


    // fragmentsState (first fragment ( fields ), second fragment( otp validation )
    private val _currentScreen = mutableStateOf(SignUpFragments.FIRST_FRAGMENT)
    val currentScreen: State<SignUpFragments> = _currentScreen

    val isInputEnabled = mutableStateOf(false)
    val accountOptions = Role.entries.map { translateRole(it) }
    var accountOption by mutableStateOf(accountOptions[1])


    // all signup fields
    val fields = mapOf(
        SignupFields.FULLNAME to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_person_outline_24,
            label = "full name",
            supportErrorMessage = "full name with alphabet characters and space",
            onErrorMessage = mutableStateOf(null),
            validator = "[a-zA-Z ]{2,254}".toRegex(),
        ),

        SignupFields.EMAIL to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_email_24,
            label = "email",
            supportErrorMessage = "email not valid, please check your email",
            onErrorMessage = mutableStateOf(null),
            validator = """^[a-zA-Z][a-zA-Z0-9_.-]*[a-zA-Z0-9]@[a-zA-Z][a-zA-Z-]*[a-zA-Z](\.[a-zA-Z]{2,})+$""".toRegex(),
        ),
        SignupFields.PHONE_NUMBER to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_smartphone_24,
            label = "phone number",
            supportErrorMessage = "phone number not valid",
            onErrorMessage = mutableStateOf(null),
            validator = """[0-9]{8}""".toRegex(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        ),

        SignupFields.PASSWORD to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_lock_24,
            label = "password",
            supportErrorMessage = "password at least 6 character",
            onErrorMessage = mutableStateOf(null),
            validator = """.{6,}""".toRegex(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    )

    val filedOfOtp = BundledTextField(
        value = mutableStateOf(""),
        validator = """[0-9]{6}""".toRegex()
    )

    fun resetInput() {
        fields.forEach { _, value ->
            value.value.value = ""
        }
    }

    fun restUiState() {
        _uiState.value = UiState.Idle
    }

}


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepositoryStandards,
    private val shardPref: ShardPref
) : SignUpViewModelBase() {

    override fun signUp() {
        fields.forEach { (_, value) ->
            value.clearValidation()
        }
        fields.filter { (key, value) -> !value.validate() }.isNotEmpty().also {
            if (it)
                return
        }
        userRepository.signup(
            email = fields[SignupFields.EMAIL]!!.value.value,
            password = fields[SignupFields.PASSWORD]!!.value.value,
            name = fields[SignupFields.FULLNAME]!!.value.value.split(" ")[0],
            phone = fields[SignupFields.PHONE_NUMBER]!!.value.value,
            role = accountOption,
            lastName = fields[SignupFields.FULLNAME]!!.value.value.split(" ")
                .getOrElse(1) { "unspecified" },
        ).onEach {
            when (it) {
                is Events.LoadingEvent -> {
                    _uiState.emit(UiState.Loading())
                }

                is Events.ErrorEvent -> {
                    _uiState.emit(UiState.Error(it.error))
                }

                is Events.SuccessEvent -> {
                    shardPref.putToken(it.data!!.accessToken, it.data.refreshToken)
                    _uiState.emit(UiState.NavigateEvent(message = "OTP sanding") {
                        navigate(
                            CodeOTP(
                                email = fields[SignupFields.EMAIL]!!.value.value,
                            )
                        ) {
                            popUpTo(0)
                        }
                    }
                    )
                }
            }
        }.catch {
            _uiState.emit(
                UiState.Error(
                    it.localizedMessage ?: "unexpected error just happened, we're sorry"
                )
            )
        }
            .launchIn(viewModelScope)
    }
}

class SignupViewModelPreview : SignUpViewModelBase() {
    override fun signUp() {
        fields.forEach { (_, u) ->
            u.clearValidation()
        }
        fields.filter { (_, value) ->
            !value.validate()
        }.isNotEmpty().also {
            if (it)
                return
        }


        viewModelScope.launch {
            _uiState.emit(UiState.Loading())
            delay(1000)
            _uiState.emit(UiState.Success(message = "Success State"))
        }
    }

}



