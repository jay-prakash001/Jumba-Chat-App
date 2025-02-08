package com.jp.chatapp.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStorePref(private val context: Context)  {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

    }


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