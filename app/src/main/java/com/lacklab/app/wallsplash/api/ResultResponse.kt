package com.lacklab.app.wallsplash.api

sealed interface ResultResponse <T> {
    class Success<T>(val data: T, val code: Int) : ResultResponse<T>

    class Loading<T> : ResultResponse<T>

    class ApiError<T>(val message: String, val code: Int) : ResultResponse<T>

    class NetworkError<T>(val throwable: Throwable) : ResultResponse<T>

    class NullResultResponse<T> : ResultResponse<T>
}