package com.jp.chatapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatList")
data class ChatList(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val phone : String,
    val lastChat: String // chat Id as string
)
