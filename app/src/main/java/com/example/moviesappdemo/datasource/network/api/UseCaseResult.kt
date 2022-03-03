package com.example.moviesappdemo.datasource.network.api


sealed class UseCaseResult<out T> {
    data class Success<out T>(val data : T) : UseCaseResult<T>()
    data class Error<out T>(val message: String? = "Unknown error", val data: T? = null): UseCaseResult<T>()
}
