package com.jp.chatapp.domain.models.user2

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)