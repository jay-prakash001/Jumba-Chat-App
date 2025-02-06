package com.jp.chatapp.old

import kotlinx.serialization.Serializable
@Serializable
data object Splash

@Serializable
data object Login

@Serializable
data class HomeRoute(val token : String = "")

@Serializable
data class ChatRoute(val name: String,
                val receiver: String,
                val profileImg: String?,
                val phone: String,
                val bio: String,
                val isOnline: Boolean = false,
                val lastSeen: String,
)

@Serializable
data object AddContactRoute