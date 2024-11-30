package com.example.projetandroid


// establish communication between viewModels and repository
// repository -(Event)-> viewModels


sealed class Events<T> {
    data class SuccessEvent<T>(
        val data: T,
        val successMessage: String? = null
    ) : Events<T>()

    data class ErrorEvent<T>(
        val error: String,
    ) : Events<T>()

    data class LoadingEvent<T>(
        val isLoading: Boolean = true
    ) : Events<T>()
}

