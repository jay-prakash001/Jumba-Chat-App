package com.jp.chatapp.domain.models.contactList

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val __v: Int,
    val _id: String,
    val bio: String,
    val phone: String,
    val profileImg: String,
    val updatedAt: String
)