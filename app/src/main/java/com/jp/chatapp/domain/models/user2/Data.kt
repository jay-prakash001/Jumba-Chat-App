package com.jp.chatapp.domain.models.user2

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val accessToken: String,
    val refreshToken: String,
    val user: UserX
)