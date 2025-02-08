package com.jp.chatapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val phone :String)