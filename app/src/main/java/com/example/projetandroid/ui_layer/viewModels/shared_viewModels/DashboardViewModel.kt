package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import com.example.projetandroid.ui_layer.shared.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class EditProfileFields {
    USERNAME,
    LASTNAME,
    PHONE_NUMBER,
    EMAIL
}


enum class StatusTriggered {
    USERPROFILE_UPDATED,
    USER_LOGGED_OUT,
    USER_DELETED,
}


@HiltViewModel
class DashboardViewModel @Inject constructor(
    val userRepository: UserRepository,
    val shardPref: ShardPref
) : ViewModel() {
    val token = shardPref.getToken()
    var user: User? = null

    val editProfileFieldsMap = mapOf<EditProfileFields, MutableState<String>>(
        EditProfileFields.USERNAME to mutableStateOf(""),
        EditProfileFields.LASTNAME to mutableStateOf(""),
        EditProfileFields.PHONE_NUMBER to mutableStateOf(""),
    )

    // triggered state -> message
    private val _triggeredState = mutableStateMapOf<StatusTriggered, String>()
    val triggeredState: SnapshotStateMap<StatusTriggered, String> = _triggeredState

    val editProfileFieldsCallbacksMap = mapOf(
        EditProfileFields.USERNAME to { value: String ->
            editProfileFieldsMap[EditProfileFields.USERNAME]!!.value = value
        },
        EditProfileFields.LASTNAME to { value: String ->
            editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value = value
        },
        EditProfileFields.PHONE_NUMBER to { value: String ->
            editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value = value
        },
    )

    private var _errorMap = mutableStateMapOf<EditProfileFields, String>()
    val errorMap: SnapshotStateMap<EditProfileFields, String> = _errorMap

    private val _state = mutableStateOf(ScreenState<User>(data = null))
    val state: State<ScreenState<User>> = _state


    private val _isAccountDeleted = mutableStateOf(false)
    val isAccountDeleted: State<Boolean> = _isAccountDeleted


    init {
        loadCurrentUser()
    }


    fun clearTriggeredState() {
        _triggeredState.clear()
    }

    fun clearUpdateState() {
        editProfileFieldsMap[EditProfileFields.USERNAME]!!.value = user?.name ?: "unset"
        editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value = user?.lastName ?: "unset"
        editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value = user?.phone ?: "unset"
    }

    fun loadCurrentUser() {
        println("DASHBOARD INITIALIZED")
        userRepository.currentUser(token).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                    _state.value = ScreenState(errorMessage = it.error)
                }

                is Events.SuccessEvent -> {
                    _state.value = ScreenState(data = it.data)
                    it.data!!
                    user = it.data
                    editProfileFieldsMap[EditProfileFields.USERNAME]!!.value =
                        it.data.name ?: "unset"
                    editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value =
                        it.data.lastName ?: "unset"
                    editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value =
                        it.data.phone ?: "unset"
                }

                is Events.LoadingEvent -> {
                    _state.value = ScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun isValueChanged(): Boolean {
        return editProfileFieldsMap[EditProfileFields.USERNAME]!!.value != (user?.name
            ?: "unset") ||
                editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value != (user?.lastName
            ?: "unset") ||
                editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value != (user?.phone
            ?: "unset")
    }


    fun updateUser() {
        if (_errorMap.isNotEmpty())
            _errorMap.clear()
        // validation part
        if (editProfileFieldsMap[EditProfileFields.USERNAME]!!.value.isEmpty()) {
            _errorMap[EditProfileFields.USERNAME] = "username is required"
        }
        if (editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value.isEmpty()) {
            _errorMap[EditProfileFields.LASTNAME] = "username is required"
        }
        if (editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value.length != 8) {
            _errorMap[EditProfileFields.PHONE_NUMBER] = "phone number exactly 8 characters"
        }
        if (_errorMap.isNotEmpty())
            return

        userRepository.updateCurrentUser(
            token,
            UpdateModel(
                name = editProfileFieldsMap[EditProfileFields.USERNAME]!!.value,
                lastName = editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value,
                phone = editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value
            )
        )
            .onEach {
                when (it) {
                    is Events.ErrorEvent -> {
                        _state.value = ScreenState(errorMessage = it.error)
                    }

                    is Events.LoadingEvent -> {
                        println("loading")
                        _state.value =
                            ScreenState(data = null, isLoading = true)
                    }

                    is Events.SuccessEvent -> {
                        _triggeredState[StatusTriggered.USERPROFILE_UPDATED] =
                            "profile updated successfully!"
                        it.data!!
                        _state.value = ScreenState(
                            data = it.data,
                            successMessage = "Update With Success State"
                        )
                        user = it.data
                    }

                    else -> {}
                }

            }
            .catch {
                _state.value = ScreenState(
                    errorMessage = it.localizedMessage ?: "unexpected error just happened"
                )
            }
            .launchIn(viewModelScope)
    }


    fun clearErrorMap() {
        _errorMap.clear()
    }

    fun clearNetwork() {
        _state.value = ScreenState()
    }

    fun delete() {
        userRepository.deleteAccount(token, user!!.id)
            .onEach {
                when (it) {
                    is Events.ErrorEvent -> {
                        _state.value = ScreenState(errorMessage = it.error)
                    }

                    is Events.LoadingEvent -> {
                        _state.value =
                            ScreenState(data = null, isLoading = true)
                    }

                    is Events.SuccessEvent -> {
                        _isAccountDeleted.value = true
                    }

                    else -> {}
                }

            }.launchIn(viewModelScope)
    }


    fun clearContext() {
        shardPref.clearToken()
    }

}