package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.data.websocket.WebSocketManager
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.contactList.ContactUserInfo
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.data.dataStore.DataStorePref
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.utils.ACCESS_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val webSocketManager: WebSocketManager, private val dataStore: DataStore,
    private val apiDataRepo: APIDataRepo
) : ViewModel() {


    private val _chatList = MutableStateFlow<List<MutableStateFlow<ChatUserInfo>>>(emptyList())
    val chatList = _chatList.asStateFlow()


    private val _contactList = MutableStateFlow<List<ContactUserInfo>>(emptyList())
    val contactList = _contactList.asStateFlow()


    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    init {

        println("created home")
        getToken()
//        getChatList()
    }

    private fun getToken() {
        viewModelScope.launch {
            dataStore.getTokenValue(ACCESS_TOKEN).collectLatest {
                println("Token refreshed ")
                _accessToken.value = it
                receiveChatList()
                println("receiving the chat list0")

            }
        }
    }


    fun receiveChatList() {
        println("receiving the chat list")
        viewModelScope.launch {
            webSocketManager.chatList { chatI ->

                println("chat list00 $chatI")
                val existingItem = _chatList.value.find { it.value.phone == chatI.phone }
                if (existingItem != null) {
                    // Update existing item inside the MutableStateFlow
                    existingItem.value = chatI
                } else {
                    // Add a new item to the list
                    _chatList.value += MutableStateFlow(chatI)
                }

            }
            println("chat list ${_chatList.value}")
            getChatList()
        }
    }


    fun getChatList() {
        viewModelScope.launch {
            if (!_accessToken.value.isNullOrBlank()) {

                webSocketManager.getChatList(_accessToken.value!!)
            }
        }
//        receiveChatList()
    }

    fun getContacts() {
        viewModelScope.launch {
            if (!_accessToken.value.isNullOrBlank()) {
                apiDataRepo.getContacts(token = _accessToken.value!!).collectLatest { contact ->
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
    }

    fun addContact(name: String, phone: String) {
        viewModelScope.launch {
            if(_accessToken.value.isNullOrBlank()) return@launch
            apiDataRepo.addUser(phone, name, _accessToken.value!!).collectLatest { newContact ->
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