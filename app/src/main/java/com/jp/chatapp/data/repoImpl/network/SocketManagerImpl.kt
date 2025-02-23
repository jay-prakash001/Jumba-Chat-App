package com.jp.chatapp.data.repoImpl.network

import com.jp.chatapp.data.network.websocket.WebSocketManager
import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.data.utils.ChatType
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.personalChat.PersonalChat
import com.jp.chatapp.domain.repo.SocketManagerRepo

class SocketManagerImpl(private val socketManager: WebSocketManager) : SocketManagerRepo {
    override fun connect(url: String) {
        socketManager.connect(url)
    }

    override fun removeCon(token: String) {
        socketManager.removeCon(token)
    }

    override fun join(token: String, firebaseMessagingToken: String) {
        socketManager.join(token, firebaseMessagingToken)
    }





    override fun sendMessage(token: String, chat : Chat) {

        socketManager.sendMessage(token, chat)
    }

    override fun syncChats(token: String, receiverId: String) {
        TODO("Not yet implemented")
    }



    override fun receiveChat(callBack: (Chat) -> Unit) {
        socketManager.receiveChat(callBack)
    }


    override fun getUserInfo(token: String, phone: String   ) {
        socketManager.getUserInfo(token, phone)
    }

    override fun getProfile(token: String) {
        socketManager.getProfile(token)
    }

    override fun receiverUserInfo(callBack: (ChatUserInfo) -> Unit) {
        socketManager.receiverUserInfo(callBack)
    }

    override fun receiveProfile(callBack: (ChatUserInfo) -> Unit) {
        socketManager.receiveProfile(callBack)
    }

    override fun updateProfileImg(file: ByteArray, fileType: String, token : String) {
        socketManager.updateProfileImg(file, fileType, token)
    }

}