package com.jp.chatapp.domain.repo

import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.data.room.entities.Contact
import com.jp.chatapp.domain.models.temp.ContactTest
import kotlinx.coroutines.flow.Flow

interface LocalRoomDbRepo {

//    fun getUserInfo(phone: String)
   suspend fun sendChat(chat: Chat)
    fun getChats(phone: String) : Flow<List<Chat>>

    //only local
   suspend fun addContact(contact: Contact)
    fun getContacts() : Flow<List<Contact>>

//    fun addChatList()
//    fun getChatList()
//    fun updateProfile()
//    fun updateContact(phone: String,bio: String, profileImg : String)

}