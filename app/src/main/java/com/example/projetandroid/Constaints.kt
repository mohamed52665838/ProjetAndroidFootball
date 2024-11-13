package com.example.projetandroid

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