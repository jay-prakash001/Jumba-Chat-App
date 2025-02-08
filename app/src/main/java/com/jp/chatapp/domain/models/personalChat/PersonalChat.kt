package com.jp.chatapp.domain.models.personalChat

import kotlinx.serialization.Serializable

@Serializable
data class PersonalChat(
    val chat: Chat,
    val senderBio: String
)