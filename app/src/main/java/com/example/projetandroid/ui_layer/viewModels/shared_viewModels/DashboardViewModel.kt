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
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.SoccerFieldRepository
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.SoccerField
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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
    PROGRESS,
    ERROR_FETCH_SOCCER_FIELD,
    GOT_OWN_SOCCER_FIELD,
    HAVE_NO_SOCCER_FIELD,
}


@HiltViewModel
class DashboardViewModel @Inject constructor(
    val userRepository: UserRepository,
    val soccerFieldRepository: SoccerFieldRepository,
    val shardPref: ShardPref
) : ViewModel() {
    val token = shardPref.getToken()
    var user: User? = null

    // manager field
    var soccerFields: SoccerField? = null

    val editProfileFieldsMap = mapOf<EditProfileFields, MutableState<String>>(
        EditProfileFields.USERNAME to mutableStateOf(""),
        EditProfileFields.LASTNAME to mutableStateOf(""),
        EditProfileFields.PHONE_NUMBER to mutableStateOf(""),
    )


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


    private val _isAccountDeleted = mutableStateOf(false)
    val isAccountDeleted: State<Boolean> = _isAccountDeleted


    // ui state


    private val _homeUiState = MutableStateFlow<UiState>(UiState.Idle)
    val homeUiState: SharedFlow<UiState> = _homeUiState

    fun homeRestore() {
        _homeUiState.value = UiState.Idle
    }

    private val _activityUiState = MutableStateFlow<UiState>(UiState.Idle)
    val activityUiState: SharedFlow<UiState> = _activityUiState

    fun activityRestore() {
        _homeUiState.value = UiState.Idle
    }

    private val _profileUiState = MutableStateFlow<UiState>(UiState.Idle)
    val profileUiState: SharedFlow<UiState> = _profileUiState

    fun profileRestore() {
        _profileUiState.value = UiState.Idle
    }

    init {
        loadCurrentUser()
    }


    fun clearUpdateState() {
        editProfileFieldsMap[EditProfileFields.USERNAME]!!.value = user?.name ?: "unset"
        editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value = user?.lastName ?: "unset"
        editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value = user?.phone ?: "unset"
    }

    fun loadCurrentUser() {

        userRepository.currentUser(token).onEach {
            when (it) {

                is Events.ErrorEvent -> {
                }

                is Events.SuccessEvent -> {
                    _homeUiState.emit(UiState.Success("User Got Successfully"))
                    it.data!!
                    user = it.data
                    editProfileFieldsMap[EditProfileFields.USERNAME]!!.value =
                        it.data.name ?: "unset"
                    editProfileFieldsMap[EditProfileFields.LASTNAME]!!.value =
                        it.data.lastName ?: "unset"
                    editProfileFieldsMap[EditProfileFields.PHONE_NUMBER]!!.value =
                        it.data.phone ?: "unset"

                    if (it.data.role == "manager") {
                        soccerFieldRepository.own(token).onEach {
                            when (it) {
                                is Events.LoadingEvent -> {
                                    _homeUiState.emit(UiState.Loading())
                                }

                                is Events.ErrorEvent -> {
                                    if (it.error == "HNSF") {
                                        _homeUiState.emit(UiState.Error("-1"))
                                    } else {
                                        _homeUiState.emit(UiState.Error(it.error))
                                    }
                                }

                                is Events.SuccessEvent -> {
                                    _homeUiState.emit(UiState.Error("Got Soccer Field"))
                                    this.soccerFields = it.data
                                }
                            }
                        }.launchIn(viewModelScope)
                    }
                }

                is Events.LoadingEvent -> {
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

    // problem with profile

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
                        _profileUiState.emit(UiState.Error(it.error))
                    }

                    is Events.LoadingEvent -> {
                        _profileUiState.emit(UiState.Loading())
                    }

                    is Events.SuccessEvent -> {
                        _profileUiState.emit(UiState.Success("Profile updated successfully"))
                        it.data!!
                        user = it.data
                    }

                    else -> {}
                }

            }
            .catch {

            }
            .launchIn(viewModelScope)
    }


    fun clearErrorMap() {
        _errorMap.clear()
    }


    fun delete() {
        userRepository.deleteAccount(token, user!!.id)
            .onEach {
                when (it) {
                    is Events.ErrorEvent -> {
                        _homeUiState.emit(UiState.Error(it.error))
                    }

                    is Events.LoadingEvent -> {
                        _homeUiState.emit(UiState.Loading())
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

    override fun onCleared() {
        super.onCleared()
        println("view Model Cleaned up")
    }

}