package com.jp.chatapp.domain.models.chatList

import kotlinx.serialization.Serializable

@Serializable
data class ChatUserInfo(
    val name: String,
    val user_id: String,
    val profileImg: String?,
    val phone: String,
    val bio: String,
    val isOnline: Boolean = false,
    val lastSeen: String,
    val lastChat: LastChat? = null


)


@Serializable
data class LastChat(val isSended: Boolean = false, val content: String, val time: String)

