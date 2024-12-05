package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.Role
import com.example.projetandroid.ShardPref
import com.example.projetandroid.SignIn
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.repository.SoccerFieldRepository
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.SoccerField
import com.example.projetandroid.model.UpdateModel
import com.example.projetandroid.model.User
import com.example.projetandroid.translateRole
import com.google.android.gms.maps.model.Dash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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


interface DashboardViewModelProtocol {
    fun loadCurrentUser()
    fun restUiState()
    fun clearContext()
    fun updateUser(user: User)
    fun isUser(): Boolean
    fun isManager(): Boolean
    fun isMineById(resourceId: String): Boolean

}

abstract class DashboardViewModelBase : ViewModel(), DashboardViewModelProtocol {
    protected val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user
    protected val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: SharedFlow<UiState> = _uiState
    override fun restUiState() {
        _uiState.value = UiState.Idle
    }

    override fun updateUser(user: User) {
        _user.value = user
    }

    override fun isManager(): Boolean {
        return user.value?.role == translateRole(Role.MANAGER)
    }

    override fun isUser(): Boolean {
        return user.value?.role == translateRole(Role.USER)
    }

    override fun isMineById(resourceId: String): Boolean {
        return user.value?.id == resourceId
    }


}


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPref: ShardPref
) : DashboardViewModelBase() {
    init {
        loadCurrentUser()
    }

    override fun loadCurrentUser() {
        userRepository.currentUser().onEach {
            when (it) {
                is Events.LoadingEvent -> {
                }

                is Events.ErrorEvent -> {
                    _uiState.emit(UiState.NavigateEvent(message = "same things went wrong") {
                        navigate(SignIn) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    })
                }

                is Events.SuccessEvent -> {
                    restUiState()
                    _user.value = it.data
                }
            }
        }.catch {
            _uiState.emit(UiState.NavigateEvent(message = "same things went wrong") {
                navigate(SignIn) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            })
        }.launchIn(viewModelScope)
    }

    override fun clearContext() {
        sharedPref.clearToken()
    }


}


class DashboardViewModelPreview : DashboardViewModelBase() {
    init {
        _user.value = User(
            id = "fafarwrijoium32m",
            active = true,
            email = "email@go.com",
            name = "username",
            phone = "phone",
            lastName = "lastname",
            role = translateRole(Role.USER)
        )
    }

    override fun loadCurrentUser() {
        _user.value = User(
            id = "fafarwrijoium32m",
            active = true,
            email = "email@go.com",
            name = "username",
            phone = "phone",
            lastName = "lastname",
            role = translateRole(Role.USER)
        )

    }

    override fun clearContext() {
        TODO("Not yet implemented")
    }

}





