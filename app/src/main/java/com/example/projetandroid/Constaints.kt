package com.example.projetandroid

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

val BASE_URL = "http://10.0.2.2:4000"
val ADDRESS_SEARCH_AP_URI = "https://nominatim.openstreetmap.org"
val TOKEN_TYPE = "Bearer " // keep it secret ;)

enum class Fields {
    PASSWORD,
    EMAIL
}

enum class SignupFields {
    EMAIL,
    PASSWORD,
    CONFIRMATION,
    NAME,
    LASTNAME,
    PHONE_NUMBER
}


enum class SignUpFragments {
    FIRST_FRAGMENT,
    SECOND_FRAGMENT
}


enum class DashboardProfile {
    USERNAME,
    PHONE_NUMBER
}

fun fromStateCodeToDeveloperMessage(
    statusCodes: Int,
    surfix: String = "",
    replacement: String = ""
): String =
    when (statusCodes) { /* response error is not joke and thanks */
        200 -> replacement.ifBlank { "Action Done Successfully, $surfix" }
        201 -> replacement.ifBlank { "Resource Created Successfully, $surfix" }
        204 -> replacement.ifBlank { "Success With No Response, $surfix" }
        in 200..299 -> replacement.ifBlank { "Action Done Successfully, $surfix" }
        404 -> replacement.ifBlank { "problem with uri, rapport issue please, $surfix" }
        in intArrayOf(
            422,
            401
        ) -> replacement.ifBlank { "bad data has been sent request, rapport issue please" }

        403 -> replacement.ifBlank { "access for bidden check, if you see that's problem please rapport issue" }
        401 -> replacement.ifBlank { "sorry unauthorized request, $surfix" }
        in intArrayOf(
            500,
            599
        ) -> replacement.ifBlank { "server problem, rapport issue please" }

        else -> replacement.ifBlank { "we are sorry same things went wrong, we are working hard to fix it" }
    }

data class BundledTextField(
    val value: MutableState<String>,
    val onChange: (value: String) -> Unit = { value.value = it },
    val iconId: Int? = null,
    val label: String? = null,
    val onErrorMessage: MutableState<String?>? = null,
    val supportErrorMessage: String? = null,
    val validator: Regex? = null,
    val keyboardOptions: KeyboardOptions? = null
) {
    fun validate(): Boolean {
        onErrorMessage?.let { errorMessage ->
            validator?.let { validator_ ->
                if (value.value.matches(validator_)) {
                    errorMessage.value = null
                    return true
                } else {
                    errorMessage.value = supportErrorMessage
                    return false
                }
            }
        }
        // no validation
        return true
    }

    fun clearValidation() {
        onErrorMessage?.value = null
    }
}


enum class SoccerFieldSubmissionFragments {
    FORM_FIRST_FRAGMENT,
    MAP_SECOND_FRAGMENT
}

sealed class UiState {
    data object Idle : UiState()
    data class Loading(val message: String? = null) : UiState()
    data class Error(val message: String, val raisonCode: Int = 0) : UiState()
    data class Success(val message: String, val operationCode: Int = 0) : UiState()
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val parentNavRoute = destination.parent?.route ?: return hiltViewModel()
    val parentBackStackEntry = remember(navController) {
        navController.getBackStackEntry(parentNavRoute)
    }
    return hiltViewModel(parentBackStackEntry)

}
