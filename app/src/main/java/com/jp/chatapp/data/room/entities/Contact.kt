package com.jp.chatapp.data.room.entities

import androidx.compose.ui.res.stringResource
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jp.chatapp.R
import com.jp.chatapp.data.utils.DEFAULT_BIO

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey
    val phone : String,
    val bio : String = DEFAULT_BIO,
    val profileImg : String?,
    val name : String

)