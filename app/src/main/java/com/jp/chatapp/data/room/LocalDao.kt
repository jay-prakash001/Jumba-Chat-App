package com.jp.chatapp.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.data.room.entities.Contact
import com.jp.chatapp.domain.models.temp.ContactTest
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    //    fun getUserInfo(phone: String)
    @Upsert
    suspend fun sendChat(chat: Chat)

    @Query("SELECT * FROM chat WHERE sender = :phone OR receiver = :phone")
    fun getChats(phone: String): Flow<List<Chat>>

    //
    @Upsert
    suspend fun addContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getContacts(): Flow<List<Contact>>


//
//    fun addChatList()
//    fun getChatList()
//    fun updateProfile()
}