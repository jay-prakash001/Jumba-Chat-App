package com.jp.chatapp.data.repoImpl.room

import com.jp.chatapp.data.room.LocalDao
import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.data.room.entities.Contact
import com.jp.chatapp.domain.models.temp.ContactTest
import com.jp.chatapp.domain.repo.LocalRoomDbRepo
import kotlinx.coroutines.flow.Flow

class LocalRoomDbImpl(private val dao: LocalDao) : LocalRoomDbRepo {
    override suspend fun sendChat(chat: Chat) {
        dao.sendChat(chat = chat)
    }

    override fun getChats(phone: String): Flow<List<Chat>> {
        return dao.getChats(phone)
    }

    override suspend fun addContact(contact: Contact) {
        dao.addContact(contact)
    }

    override fun getContacts(): Flow<List<Contact>> {
        return dao.getContacts()
    }




}