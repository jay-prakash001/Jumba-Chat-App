package com.jp.chatapp.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jp.chatapp.data.room.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

//    fun getUserInfo(phone: String)
//    fun sendChat()
//    fun getChats(phone: String)
//
    @Upsert
   suspend fun addContact(contact: Contact)

   @Query("SELECT * FROM contact")
    fun getContacts() : Flow<List<Contact>>

//
//    fun addChatList()
//    fun getChatList()
//    fun updateProfile()
}