package com.jp.chatapp.old.domain.models.personalChat

import kotlinx.serialization.Serializable

@Serializable
data class PersonalChat(
    val chat: Chat,
    val senderBio: String
)