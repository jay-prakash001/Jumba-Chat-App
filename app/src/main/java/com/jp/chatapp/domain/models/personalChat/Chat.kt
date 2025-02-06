package com.jp.chatapp.old.domain.models.personalChat

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val __v: Int,
    val _id: String,
    val content: String,
    val createdAt: String,
    val `receiver`: String,
    val sender: String,
    val updatedAt: String
)