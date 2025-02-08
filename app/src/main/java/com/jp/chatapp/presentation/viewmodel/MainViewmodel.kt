package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.jp.chatapp.data.websocket.WebSocketManager
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.utils.ACCESS_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewmodel(
    private val dataStore: DataStore,
    private val webSocketManager: WebSocketManager
) : ViewModel() {


    private val _accessToken = MutableStateFlow<ResultState<String?>>(ResultState.Loading)

    val accessToken = _accessToken.onStart {
        getToken(ACCESS_TOKEN)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(0),
        ResultState.Loading
    )

    private val _isJoined = MutableStateFlow<Boolean>(false)
    val isJoined = _isJoined.asStateFlow()
    fun getToken(key: String) {
        viewModelScope.launch {


            dataStore.getToken(key).collectLatest { tokenResult ->
                _accessToken.update { tokenResult }
            }

        }
    }


    fun connect() {
        viewModelScope.launch {
            webSocketManager.connect()
        }
        println("Connected Joined ${accessToken.value}")
        joinSocket()
    }

     private fun joinSocket() {
        viewModelScope.launch {
            accessToken.collectLatest { it ->
                println("AccessToken $it")
                when (it) {

                    is ResultState.Success -> {

                        if (!it.data.isNullOrBlank()) {
                            val fcmToken = Firebase.messaging.token.await()
                            webSocketManager.join(it.data, fcmToken)
                            _isJoined.value = true
                            println(_isJoined.value )
                            println("joined")
                        }

                    }

                    else -> {
                    println("Error $it")
                    }
                }

            }


        }
    }


    fun receiveChatList(callBack : (String)->Unit){
        viewModelScope.launch {
            webSocketManager.chatList {
                callBack(it.toString())
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