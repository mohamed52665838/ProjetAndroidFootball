package com.example.projetandroid.ui_layer.shared

// Note: All data screens have unified state!

data class ScreenState<T>(
    val errorMessage: String? = null,
    val successMessage: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val data: T? = null
)
