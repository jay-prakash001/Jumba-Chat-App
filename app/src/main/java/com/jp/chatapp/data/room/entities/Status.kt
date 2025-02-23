package com.jp.chatapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jp.chatapp.data.utils.ChatType

@Entity(tableName =  "status")
data class Status(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val content : String? = null,
    val contentUri : String? = null,
    val time : Long = System.currentTimeMillis(),
    val type : ChatType,
    val ownerPhone : String
)
