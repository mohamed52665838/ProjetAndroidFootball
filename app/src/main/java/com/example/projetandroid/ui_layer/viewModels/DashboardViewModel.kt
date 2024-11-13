package com.example.projetandroid.ui_layer.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.DashboardProfile
import com.example.projetandroid.Events
import com.example.projetandroid.Fields
import com.example.projetandroid.ShardPref
import com.example.projetandroid.SignupFields
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.TokenModel
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import com.example.projetandroid.ui_layer.shard.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val userRepository: UserRepository,
    val shardPref: ShardPref
) : ViewModel() {
    val token = shardPref.getToken()
    var user: User? = null
    var username by mutableStateOf("")
    var lastname by mutableStateOf("")
    var phoneNumber by mutableStateOf("")

    private var _errorMap = mutableStateMapOf<DashboardProfile, String>()
    val errorMap: SnapshotStateMap<DashboardProfile, String> = _errorMap

    private val _state = mutableStateOf(ScreenState<User>(data = null))
    val state: State<ScreenState<User>> = _state


    private val _isAccountDeleted = mutableStateOf(false)
    val isAccountDeleted: State<Boolean> = _isAccountDeleted


    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        userRepository.currentUser(token).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                    _state.value = ScreenState(errorMessage = it.error)
                }

                is Events.SuccessEvent -> {
                    _state.value = ScreenState(data = it.data)
                    it.data!!
                    user = it.data
                    username = it.data.name ?: "unset"
                    lastname = it.data.lastName ?: "unset"
                    phoneNumber = it.data.phone ?: "unset"
                }

                is Events.LoadingEvent -> {
                    _state.value = ScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun updateUser() {
        // validation part
        if (username.isEmpty()) {
            _errorMap[DashboardProfile.USERNAME] = "username is required"
        }
        if (phoneNumber.length != 8) {
            _errorMap[DashboardProfile.PHONE_NUMBER] = "phone number exactly 8 characters"
        }
        if (_errorMap.isNotEmpty())
            return

        userRepository.updateCurrentUser(token, user!!.id, UpdateModel(username, phoneNumber))
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
                        it.data!!
                        _state.value = ScreenState(
                            data = it.data,
                            successMessage = "Update With Success State"

                        )
                        user = it.data
                    }

                    else -> {}
                }

            }.launchIn(viewModelScope)
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