package com.jp.chatapp.old.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.old.data.websocket.WebSocketManager
import com.jp.chatapp.old.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.old.domain.models.personalChat.PersonalChat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val webSocketManager: WebSocketManager) : ViewModel() {

    private val _chats = MutableStateFlow<List<MutableStateFlow<PersonalChat>>>(emptyList())
    val chats = _chats.asStateFlow()
    private val _userInfo = MutableStateFlow<ChatUserInfo?>(null)
    val userInfo = _userInfo.asStateFlow()
    init {
        println("chat created")
        receiveChats()
        receiverUserInfo()

    }

    fun getChats(accessToken: String, receiver: String) {
//        println("get Chats $receiver")
        viewModelScope.launch {
            webSocketManager.getPreviousChats(accessToken, receiver)
        }
        receiveChats()
    }

    fun clearList() {
        _chats.value = emptyList()

    }


     fun receiveChats() {
        viewModelScope.launch {
            webSocketManager.receiveChat { chat ->

                val eChat = _chats.value.find { it.value.chat._id == chat.chat._id }
                if (eChat != null) {
                    eChat.value = chat
                } else {
                    _chats.value += MutableStateFlow(chat)
                }
            }
        }
        receiverUserInfo()

    }

    fun sendMessage(token: String, receiver: String, content: String) {
        println("phone $receiver")
        viewModelScope.launch {
            webSocketManager.sendMessage(token, receiver, content)
        }

        receiveChats()

    }

    fun getUserInfo(token: String, userId: String) {
        _userInfo.value = null
        viewModelScope.launch {
            webSocketManager.getUserInfo(token, userId)
        }

        receiverUserInfo()
    }

      fun receiverUserInfo() {

    println("UserInfo ")
        viewModelScope.launch {
            webSocketManager.receiverUserInfo() { user ->
                println("User :${_userInfo.value}")


                if (_userInfo.value != null) {
                    if (_userInfo.value?.user_id == user.user_id) {
//                        _userInfo.value = _userInfo.value!!.copy(
//                            profileImg = user.profileImg,
//                            lastSeen = user.lastSeen
//                        )

                       val a = user.copy(name = _userInfo.value!!.name)
                        _userInfo.value = a

                    }
                } else {
                    _userInfo.value = user
                }
            }
        }
    }
}