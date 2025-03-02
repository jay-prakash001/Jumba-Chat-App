package com.jp.chatapp.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.data.room.entities.ChatList
import com.jp.chatapp.data.room.entities.Contact
import com.jp.chatapp.data.room.entities.Status

@Database(
    entities = [Chat::class, Status::class, Contact::class, ChatList::class],
    version = 1,
//    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class ChatDb : RoomDatabase() {
    abstract val dao: LocalDao
}