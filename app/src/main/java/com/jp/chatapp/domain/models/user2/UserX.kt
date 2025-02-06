package com.jp.chatapp.old.domain.models.user2

import kotlinx.serialization.Serializable

@Serializable
data class UserX(
    val __v: Int,
    val _id: String,
    val bio: String,
    val chats: List<String>  = emptyList(),
    val contacts: List<Contact> = emptyList(),
    val createdAt: String,
    val isOnline: Boolean,
    val phone: String,
    val profileImg: String,
    val refreshtoken: String,
    val socketId: String?= null,
    val updatedAt: String
)