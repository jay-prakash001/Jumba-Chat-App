package com.jp.chatapp.domain.repo

import com.jp.chatapp.domain.models.contactList.ContactRes
import com.jp.chatapp.domain.models.contactList.SingleContact
import com.jp.chatapp.domain.models.user2.Contact
import com.jp.chatapp.domain.models.user2.User
import com.jp.chatapp.domain.state.ResultState
import kotlinx.coroutines.flow.Flow

interface APIDataRepo {

    fun register(phone: String, file: ByteArray, bio: String): Flow<ResultState<User>>
    fun getContacts(token : String): Flow<ResultState<ContactRes>>
    fun addUser(phone: String, name : String, token: String) : Flow<ResultState<SingleContact>>
    fun login(phone : String) : Flow<ResultState<User>>
    fun updateProfileImg(file: ByteArray, token: String) :Flow<ResultState<String>>
}