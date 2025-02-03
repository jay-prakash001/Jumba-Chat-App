package com.jp.chatapp.domain.models.contactList

import kotlinx.serialization.Serializable

@Serializable
data class ContactRequest(val name : String, val phone : String)