package com.jp.chatapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jp.chatapp.data.utils.ChatType
import com.jp.chatapp.data.utils.MessageStatus
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val _id : Long ,
    val sender: String,
    val receiver: String,
    val content : String = "",
    val status : MessageStatus? = null,
    val contentUri : String?, // we will store location as link
    val type : ChatType,
    val time : Long = System.currentTimeMillis(),
    val mongoId : String? = null

)
