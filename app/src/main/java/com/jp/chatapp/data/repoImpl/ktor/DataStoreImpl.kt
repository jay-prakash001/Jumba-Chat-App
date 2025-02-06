package com.jp.chatapp.old.data.repoImpl.ktor

import com.jp.chatapp.old.DataStore
import com.jp.chatapp.old.domain.state.ResultState
import com.jp.chatapp.old.dataStore.DataStorePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

class DataStoreImpl(private val dataStorePref: DataStorePref) : DataStore {
    override suspend fun setToken(tokenName: String, tokenValue: String) {
        dataStorePref.setToken(tokenName, tokenValue)
    }

    override fun getToken(tokenName: String): Flow<ResultState<String?>> = callbackFlow {
        trySend(ResultState.Loading)
        try {
            dataStorePref.getToken(tokenName).collectLatest {
                println(" key : $it")

                trySend(ResultState.Success(it))

            }
            trySend(ResultState.Success("hello"))
        } catch (e: Exception) {
            trySend(ResultState.Error(e.localizedMessage))
        }
    }



}