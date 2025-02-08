package com.jp.chatapp.domain.models.contactList

import kotlinx.serialization.Serializable

@Serializable
data class ContactUserInfo(
    val name: String,
    val userInfo: UserInfo
)