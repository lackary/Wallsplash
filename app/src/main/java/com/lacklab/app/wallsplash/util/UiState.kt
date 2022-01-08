package com.lacklab.app.wallsplash.util

sealed class UiState<T: Any> {
    data class Loading<T: Any>(val data: T?): UiState<T>()
    data class Success<T: Any>(val data: T?): UiState<T>()
    data class Error<T: Any>(val data: T?, val message: String?): UiState<T>()
}
