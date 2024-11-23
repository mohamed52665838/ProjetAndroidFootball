package com.example.projetandroid.ui_layer.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OTACodeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val shardPref: ShardPref
) : ViewModel() {

    var code by mutableStateOf("")

    fun clearState() {
    }


    fun submitCode(email: String) {
        userRepository.verifyOtp(email, code).onEach {
            when (it) {
                is Events.ErrorEvent -> {
                }

                is Events.SuccessEvent -> {
                }

                is Events.LoadingEvent -> {
                }

                else -> {}
            }
        }.catch {
        }
            .launchIn(viewModelScope)
    }


}