package com.example.projetandroid


// establish communication between viewModels and repository
// repository -(Event)-> viewModels


sealed class Events<T> {
    class SuccessEvent<T>(
        val data: T,
        val error: String? = null,
        val isLoading: Boolean = false
    ) :
        Events<T>()

    class ErrorEvent<T>(
        val data: T? = null,
        val error: String,
        val isLoading: Boolean = false
    ) :
        Events<T>()

    class LoadingEvent<T>(
        val data: T? = null,
        val error: String? = null,
        val isLoading: Boolean = true
    ) : Events<T>()
}
