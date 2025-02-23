package com.jp.chatapp.domain.repo

import com.jp.chatapp.data.room.entities.Contact
import kotlinx.coroutines.flow.Flow

interface LocalRoomDbRepo {

//    fun getUserInfo(phone: String)
//    fun sendChat()
//    fun getChats(phone: String)

    //only local
   suspend fun addContact(contact: Contact)
    fun getContacts() : Flow<List<Contact>>

//    fun addChatList()
//    fun getChatList()
//    fun updateProfile()
//    fun updateContact(phone: String,bio: String, profileImg : String)

}