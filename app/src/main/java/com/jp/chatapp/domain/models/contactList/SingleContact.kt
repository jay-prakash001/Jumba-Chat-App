package com.jp.chatapp.domain.models.contactList

import kotlinx.serialization.Serializable

@Serializable
data class SingleContact(
    val message: String,
    val statusCode: Int,
    val success: Boolean,
    val `data`: ContactUserInfo
)