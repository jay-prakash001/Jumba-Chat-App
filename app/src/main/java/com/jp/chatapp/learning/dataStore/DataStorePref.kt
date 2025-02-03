package com.jp.chatapp.learning.dataStore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jp.chatapp.domain.state.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class DataStorePref(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

    }

//    val getAccessToken : Flow<String?> = context.dataStore.data.map { pref ->
//
//        pref[ACCESS_TOKEN] ?: "not found"
//    }

    fun getToken(key: String): Flow<String?> =  context.dataStore.data.map { pref ->
        pref[stringPreferencesKey(key)]
            }


    suspend fun setToken(key: String, value: String?) {
        try {
            context.dataStore.edit { pref ->
                if (value == null) {
                    pref.remove(stringPreferencesKey(key))
                } else {

                    pref[stringPreferencesKey(key)] = value
                }
            }
        } catch (e: Exception) {
            println("prerence $e")
        }

    }

}