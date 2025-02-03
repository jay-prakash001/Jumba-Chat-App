package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.data.websocket.WebSocketManager
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.utils.ACCESS_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewmodel(
    private val dataStore: DataStore,
    private val webSocketManager: WebSocketManager
) : ViewModel() {

    private val _accessToken = MutableStateFlow<ResultState<String?>>(ResultState.Loading)
//    val accessToken = _accessToken.onStart {
//    getToken(ACCESS_TOKEN, false)
//    }.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(0),
//        ""
//    )

    val accessToken = _accessToken.asStateFlow()


    init {

        println("main created")
        getToken(ACCESS_TOKEN, false)

    }

    fun getToken(key: String, isRefreshToken: Boolean) {
        viewModelScope.launch {
            dataStore.getToken(key).collectLatest { it ->
                when (it) {
                    is ResultState.Error -> {
                        _accessToken.value = it
                    }

                    ResultState.Loading -> {
                        _accessToken.value = it
                    }

                    is ResultState.Success -> {

                        _accessToken.value = it

                    }
                }

                println("Token $it")
////                if (isRefreshToken) _refreshToken.value = it else
//                            _accessToken.value = it
            }

        }
    }


    fun connect() {
        viewModelScope.launch {
            webSocketManager.connect()
        }
        joinSocket()
    }

    fun joinSocket() {
        viewModelScope.launch {
            accessToken.collectLatest { it ->

                when (it) {

                    is ResultState.Success -> {

                        if (!it.data.isNullOrBlank()) {
                            webSocketManager.join(it.data)
                            println("joined")
                        }

                    }

                    else -> {

                    }
                }

            }


        }
    }

    fun removeSocket() {
        viewModelScope.launch {
            accessToken.collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        if (it.data != null) {
                            webSocketManager.removeCon(it.data)
                        }
                    }

                    else -> {
                        println("remove Socket $it")
                    }
                }

            }
        }
    }
}