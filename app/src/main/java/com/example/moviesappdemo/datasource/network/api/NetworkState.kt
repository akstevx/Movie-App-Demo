package com.example.moviesappdemo.datasource.network.api

sealed class NetworkState {
    object HAS_LOADED : NetworkState()
    object IS_LOADING : NetworkState()
    data class ERROR(val msg: String): NetworkState()
}
