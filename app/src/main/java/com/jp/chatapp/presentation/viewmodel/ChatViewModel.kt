package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.data.network.websocket.WebSocketManager
import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.data.utils.ChatType
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.personalChat.PersonalChat
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.domain.repo.LocalRoomDbRepo
import com.jp.chatapp.domain.repo.SocketManagerRepo
import com.jp.chatapp.utils.ACCESS_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val webSocketManager: SocketManagerRepo,
    private val dataStore: DataStore,
    private val localRoomDbRepo: LocalRoomDbRepo
) : ViewModel() {

    //    private val _chats = MutableStateFlow<List<MutableStateFlow<PersonalChat>>>(emptyList())
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats = _chats.asStateFlow()
    private val _userInfo = MutableStateFlow<ChatUserInfo?>(null)
    val userInfo = _userInfo.asStateFlow()
    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()


    init {
        println("chat created")
        receiveChats()
        receiverUserInfo()
        getToken()

    }


    private fun getToken() {
        viewModelScope.launch {
            dataStore.getTokenValue(ACCESS_TOKEN).collectLatest {
                println("Token refreshed ")
                _accessToken.value = it
                println("receiving the chat list0")

            }
        }
    }

    fun getChats(receiver: String) {
//        println("get Chats $receiver")
        viewModelScope.launch {
            if (_accessToken.value.isNullOrBlank()) return@launch
//            webSocketManager.getPreviousChats(_accessToken.value!!, receiver)
        }
        receiveChats()
    }


    fun receiveChats() {
        viewModelScope.launch {
            webSocketManager.receiveChat { chat ->
                println("chat $chat")

                _chats.value += chat

//                val eChat = _chats.value.find { it.value.chat._id == chat.chat._id }
//                if (eChat != null) {
//                    eChat.value = chat
//                } else {
//                    _chats.value += MutableStateFlow(chat)
//                }
            }
        }
        receiverUserInfo()

    }


    fun getOfflineChats(phone: String){
        viewModelScope.launch {
            localRoomDbRepo.getChats(phone).collectLatest {
                _chats.value = it
            }
        }
    }
    fun sendMessage(receiver: String, content: String) {
        println("phone $receiver")
        if (_accessToken.value.isNullOrBlank()) return
        viewModelScope.launch {
            val chat = Chat(
                _id = System.currentTimeMillis(),
                sender = "",
                receiver = receiver,
                content = content,
                contentUri = null,
                status = null,
                type = ChatType.NORMAL
            )
            localRoomDbRepo.sendChat(chat = chat)
            webSocketManager.sendMessage(_accessToken.value!!, chat)
        }

//        receiveChats()

    }

    fun getUserInfo(phone: String) {
        println("User $phone")
        _userInfo.value = null
        if (_accessToken.value.isNullOrBlank()) return
        viewModelScope.launch {
            webSocketManager.getUserInfo(_accessToken.value!!, phone)
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