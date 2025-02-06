package com.jp.chatapp.old.domain.models.user2

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val accessToken: String,
    val refreshtoken: String,
    val user: UserX
)