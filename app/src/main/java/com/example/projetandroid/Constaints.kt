package com.example.projetandroid

import android.bluetooth.BluetoothStatusCodes

val BASE_URL = "http://10.0.2.2:4000"
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

