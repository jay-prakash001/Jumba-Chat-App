package com.jp.chatapp.old.domain.models.contactList

import kotlinx.serialization.Serializable

@Serializable
data class ContactRes(
    val `data`: List<ContactUserInfo>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)