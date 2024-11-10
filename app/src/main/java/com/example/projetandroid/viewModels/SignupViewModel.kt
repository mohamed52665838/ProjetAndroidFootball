package com.example.projetandroid.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.Fields
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {


    // email, password
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private var _errorMap = mutableStateMapOf<Fields, String>()
    val errorMap: SnapshotStateMap<Fields, String> = _errorMap
    var isLoading = mutableStateOf(false)
    var weGotData = mutableStateOf(false)


    fun clear() {
        _errorMap.clear()
        email = ""
        password = ""
    }


    fun submit() {
        _errorMap.clear()
        // validation
        // email
        val regularExpressionEmail =
            """^[a-zA-Z]{1}[a-zA-Z0-9_\.-]*[a-zA-Z]@[a-zA-Z]{1}[a-zA-Z-]*[a-zA-Z]{1}(\.[a-zA-Z]{2,})+$""".toRegex()

        if (!regularExpressionEmail.matches(email)) {
            _errorMap[Fields.EMAIL] = "invalid email, please check your email"
            println("we got error of email")
        }

        if (password.length < 6) {
            _errorMap[Fields.PASSWORD] = "password at least 6 character"
            println("we got error of password")
        }

        if (_errorMap.isNotEmpty()) {

            return
        }

        viewModelScope.launch {
            isLoading.value = true
            delay(1000)
            isLoading.value = false
        }
    }
}