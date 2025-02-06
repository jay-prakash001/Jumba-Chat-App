package com.jp.chatapp.old

import com.jp.chatapp.old.domain.models.contactList.ContactRes
import com.jp.chatapp.old.domain.models.contactList.SingleContact
import com.jp.chatapp.old.domain.models.user2.User
import com.jp.chatapp.old.domain.state.ResultState
import kotlinx.coroutines.flow.Flow

interface APIDataRepo {

    fun register(phone: String, file: ByteArray, bio: String): Flow<ResultState<User>>
    fun getContacts(token : String): Flow<ResultState<ContactRes>>
    fun addUser(phone: String, name : String, token: String) : Flow<ResultState<SingleContact>>
}