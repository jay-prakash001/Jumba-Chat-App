package com.jp.chatapp.domain.state

sealed class ResultState<out T> {

    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val msg: String) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}

 data class State<T>(val isLoading: Boolean = false,
                     val error: String = "error",
                     val data : T? = null)

