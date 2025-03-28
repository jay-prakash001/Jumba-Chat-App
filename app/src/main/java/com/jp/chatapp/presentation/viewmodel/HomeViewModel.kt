package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.data.room.entities.Contact
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.contactList.ContactUserInfo
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.domain.repo.LocalRoomDbRepo
import com.jp.chatapp.domain.repo.SocketManagerRepo
import com.jp.chatapp.utils.ACCESS_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val webSocketManager: SocketManagerRepo,
    private val dataStore: DataStore,
    private val apiDataRepo: APIDataRepo,
    private val localRoomDbRepo: LocalRoomDbRepo
) : ViewModel() {

    private val _profile = MutableStateFlow<ChatUserInfo?>(null)
    val profile = _profile.asStateFlow()

    private val _chatList = MutableStateFlow<List<MutableStateFlow<ChatUserInfo>>>(emptyList())
    val chatList = _chatList.asStateFlow()


    private val _contactList = MutableStateFlow<List<Contact>>(emptyList())
    val contactList = _contactList.asStateFlow()


    private val _accessToken = MutableStateFlow<String?>(null)

    init {

        println("created home")
        getToken()
    }

    private fun getToken() {
        viewModelScope.launch {
            dataStore.getTokenValue(ACCESS_TOKEN).collectLatest {
                println("Token refreshed ")
                _accessToken.value = it
                getProfile()
                println("receiving the chat list0")

            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.setToken(ACCESS_TOKEN, null)
        }
    }

    fun getChatList(){
        // TODO:  to be implemented with room
    }

     fun updateContacts(){
        viewModelScope.launch {
            _contactList.value.forEach { contact->
                apiDataRepo.getContact(contact.phone).collectLatest {
                    val updatedContact = contact.copy(bio = it.bio, profileImg = it.profileImg)

                    localRoomDbRepo.addContact(updatedContact)
                }
            }
        }
    }





//    fun receiveChatList() {
//        // to be implemented with room
//        println("receiving the chat list")
//        viewModelScope.launch {
////            webSocketManager.chatList { chatI ->
////
////                println("chat list00 $chatI")
////                val existingItem = _chatList.value.find { it.value.phone == chatI.phone }
////                if (existingItem != null) {
////                    // Update existing item inside the MutableStateFlow
////                    existingItem.value = chatI
////                } else {
////                    // Add a new item to the list
////                    _chatList.value += MutableStateFlow(chatI)
////                }
////
////            }
//            println("chat list ${_chatList.value}")
//        }
//    }


//    fun getChatList() {
//        viewModelScope.launch {
//            if (!_accessToken.value.isNullOrBlank()) {
//
////                webSocketManager.getChatList(_accessToken.value!!)
//            }
//        }
//    }

    fun getContacts() {
        viewModelScope.launch {

            localRoomDbRepo.getContacts().collectLatest {
                println("Contacts $it")

                _contactList.value = it}

        }






//        viewModelScope.launch {
//            if (!_accessToken.value.isNullOrBlank()) {
//                apiDataRepo.getContacts(token = _accessToken.value!!).collectLatest { contact ->
//                    when (contact) {
//                        is ResultState.Error -> {
//
//                        }
//
//                        ResultState.Loading -> {
//
//                        }
//
//                        is ResultState.Success -> {
//
//                            _contactList.value = contact.data.data
//                        }
//                    }
//                }
//            }
//        }
    }

    fun addContact(name: String, phone: String) {



        viewModelScope.launch {
            val contact = Contact(phone = phone, name = name, profileImg = null)
            println(contact)
            localRoomDbRepo.addContact(contact)

        }
    }

    fun getProfile() {
        if (_accessToken.value != null) {
            viewModelScope.launch {
                webSocketManager.getProfile(_accessToken.value!!)
            }
            receiveProfile()
        }

    }

    fun receiveProfile() {
        viewModelScope.launch {
            webSocketManager.receiveProfile {
                _profile.value = it
            }
        }
    }

    fun updateProfile(file: ByteArray?, fileName: String?) {
        println("homeViewmodel token ${_accessToken.value}")
        if (file == null || fileName.isNullOrBlank()) return
        viewModelScope.launch {

            webSocketManager.updateProfileImg(file, fileName, _accessToken.value!!)
            getProfile()
        }
    }

}