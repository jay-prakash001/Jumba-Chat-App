package com.jp.chatapp.domain.repo

import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.personalChat.PersonalChat

interface SocketManagerRepo {

    fun connect(url: String = "http://10.0.2.2:9000")

    fun removeCon(token: String)
    fun join(token: String, firebaseMessagingToken: String)

    fun getChatList(token: String)
    fun chatList(callBack: (ChatUserInfo) -> Unit)


    fun sendMessage(token: String, phone: String, msg: String)
    fun getPreviousChats(token: String, receiverId: String)
    fun receiveChat(callBack: (PersonalChat) -> Unit)

    fun getUserInfo(token: String, userId: String)

    fun getProfile(token: String)
    fun receiverUserInfo(callBack: (ChatUserInfo) -> Unit)

    fun receiveProfile(callBack: (ChatUserInfo) -> Unit)


    fun updateProfileImg(file: ByteArray, fileType: String, token : String)

    fun testEnum()

}