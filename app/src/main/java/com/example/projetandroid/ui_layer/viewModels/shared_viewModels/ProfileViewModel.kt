package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.BundledTextField
import com.example.projetandroid.Events.*
import com.example.projetandroid.R
import com.example.projetandroid.ShardPref
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
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


interface ProfileViewModelProtocol {
    fun update(userUpdateCallback: (user: User) -> Unit)
    fun isChanged(): Boolean
    fun restUIState()
    fun restChanges()
    fun fillUser()
}


abstract class ProfileViewModelBase(protected val user: User) : ViewModel(),
    ProfileViewModelProtocol {
    protected val _uiState = MutableStateFlow<UiState>(UiState.Idle)


    val uiState: SharedFlow<UiState> = _uiState
    val fields = mapOf(
        EditProfileFields.USERNAME to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_person_outline_24,
            label = "first name",
            supportErrorMessage = "full name with alphabet characters and space",
            onErrorMessage = mutableStateOf(null),
            validator = "[a-zA-Z ]{2,254}".toRegex(),
        ),

        EditProfileFields.LASTNAME to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_person_outline_24,
            label = "last name",
            supportErrorMessage = "full name with alphabet characters and space",
            onErrorMessage = mutableStateOf(null),
            validator = "[a-zA-Z ]{2,254}".toRegex(),
        ),

        EditProfileFields.PHONE_NUMBER to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_smartphone_24,
            label = "phone number",
            supportErrorMessage = "phone number not valid",
            onErrorMessage = mutableStateOf(null),
            validator = """[0-9]{8}""".toRegex(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        ),

        EditProfileFields.EMAIL to BundledTextField(
            value = mutableStateOf(""),
            iconId = R.drawable.baseline_email_24,
            label = "email",
        ),
    )

    final override fun fillUser() {
        fields[EditProfileFields.USERNAME]!!.value.value = user.name
        fields[EditProfileFields.LASTNAME]!!.value.value = user.lastName
        fields[EditProfileFields.PHONE_NUMBER]!!.value.value = user.phone
        fields[EditProfileFields.EMAIL]!!.value.value = user.email
    }


    override fun restChanges() {
        fillUser()
    }

    override fun isChanged(): Boolean {
        return !(fields[EditProfileFields.USERNAME]!!.value.value == user.name &&
                fields[EditProfileFields.LASTNAME]!!.value.value == user.lastName &&
                fields[EditProfileFields.PHONE_NUMBER]!!.value.value == user.phone &&
                fields[EditProfileFields.EMAIL]!!.value.value == user.email)
    }


    override fun restUIState() {
        _uiState.value = UiState.Idle
    }


}


@HiltViewModel(assistedFactory = ProfileViewModel.ProfileViewModelAssistant::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted private val assistedUser: User,
    private val userRepository: UserRepository,
    private val sharedPref: ShardPref
) : ProfileViewModelBase(user = assistedUser) {

    @AssistedFactory
    interface ProfileViewModelAssistant {
        fun create(user: User): ProfileViewModel
    }

    init {
        fillUser()
    }

    override fun update(userUpdateCallback: (user: User) -> Unit) {

        fields.forEach { (key, value) -> value.clearValidation() }
        fields.filter { (key, value) -> !value.validate() }.isNotEmpty().also {
            if (it)
                return
        }
        userRepository.updateCurrentUser(
            sharedPref.getToken(),
            UpdateModel(
                name =
                fields[EditProfileFields.USERNAME]!!.value.value,
                lastName =
                fields[EditProfileFields.LASTNAME]!!.value.value,
                phone =
                fields[EditProfileFields.PHONE_NUMBER]!!.value.value,
            )
        ).onEach {
            when (it) {

                is LoadingEvent -> {
                    _uiState.emit(UiState.Loading("updating..."))
                }

                is ErrorEvent -> {
                    _uiState.emit(UiState.Error(message = it.error))
                }

                is SuccessEvent -> {
                    _uiState.emit(UiState.Success(message = "your data updated successfully"))
                    userUpdateCallback(it.data!!)
                }
            }
        }.catch {

            _uiState.emit(
                UiState.Error(
                    message = it.localizedMessage ?: "unexpected error just happened"
                )
            )
        }.launchIn(viewModelScope)


    }


}


class ProfileViewModelPreview(user: User) : ProfileViewModelBase(user) {
    init {
        fillUser()
    }

    override fun update(userUpdateCallback: (user: User) -> Unit) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading("updating..."))
            delay(1000)
            _uiState.emit(UiState.Success("Profile Updated successfully"))
        }
    }

}
