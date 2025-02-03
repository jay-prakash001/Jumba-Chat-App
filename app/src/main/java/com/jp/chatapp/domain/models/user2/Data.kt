package com.jp.chatapp.domain.models.user2

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val accessToken: String,
    val refreshtoken: String,
    val user: UserX
)