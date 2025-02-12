package com.jp.chatapp.domain.models.user2

data class ProfileUpdateResponse(
    val `data`: Any,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)