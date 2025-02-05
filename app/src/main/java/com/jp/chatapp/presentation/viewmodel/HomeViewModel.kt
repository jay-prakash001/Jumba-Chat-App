package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.data.websocket.WebSocketManager
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.contactList.ContactRes
import com.jp.chatapp.domain.models.contactList.ContactUserInfo
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.learning.dataStore.DataStorePref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val webSocketManager: WebSocketManager, private val dataStorePref: DataStorePref,
    private val apiDataRepo: APIDataRepo
) : ViewModel() {


    private val _chatList = MutableStateFlow<List<MutableStateFlow<ChatUserInfo>>>(emptyList())
    val chatList = _chatList.asStateFlow()


    private val _contactList = MutableStateFlow<List<ContactUserInfo>>(emptyList())
    val contactList = _contactList.asStateFlow()

    init {

        println("created home")
    }

    private fun connect() {
        viewModelScope.launch {
            webSocketManager.connect("http://10.0.2.2:9000")
        }
    }

//    fun join(token: String, firebaseToken : String) {
//        viewModelScope.launch {
//
//            if (!webSocketManager.socket.isActive)
//                webSocketManager.join(token, firebaseToken)
//        }
//        receiveChatList()
//        println("join $token")
//        getChatList(token = token)
//    }

    fun receiveChatList() {

        viewModelScope.launch {
            webSocketManager.chatList { chatI ->
//                println("$$$$$$$$$$$$$$$$44 $chatI")
                val existingItem = _chatList.value.find { it.value.phone == chatI.phone }
                if (existingItem != null) {
                    // Update existing item inside the MutableStateFlow
                    existingItem.value = chatI
                } else {
                    // Add a new item to the list
                    _chatList.value += MutableStateFlow(chatI)
                }

            }
            println(_chatList.value)
        }
    }


    fun getChatList(token: String) {
        viewModelScope.launch {
            webSocketManager.getChatList(token)
        }
        receiveChatList()
    }

    fun getContacts(token: String) {
        viewModelScope.launch {
            apiDataRepo.getContacts(token = token).collectLatest { contact ->
                when (contact) {
                    is ResultState.Error -> {

                    }

                    ResultState.Loading -> {

                    }

                    is ResultState.Success -> {

                        _contactList.value = contact.data.data
                    }
                }
            }
        }
    }

    fun addContact(name: String, phone: String, token: String) {
        viewModelScope.launch {
            apiDataRepo.addUser(phone, name, token).collectLatest { newContact ->
                when (newContact) {
                    is ResultState.Error -> {}
                    ResultState.Loading -> {}
                    is ResultState.Success -> {
                        _contactList.collectLatest { contact ->
                            val existing =
                                contact.find { it.userInfo._id == newContact.data.data.userInfo._id }
                            if (existing == null) {
                                _contactList.value += newContact.data.data
                            }
                        }


                    }
                }

            }
        }
    }

}