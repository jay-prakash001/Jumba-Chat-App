package com.jp.chatapp.presentation.navigation

import kotlinx.serialization.Serializable
@Serializable
data object Splash

@Serializable
data object Login


@Serializable
data object  Home

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
data class ShowImage(val img : String)
@Serializable
data object AddContactRoute