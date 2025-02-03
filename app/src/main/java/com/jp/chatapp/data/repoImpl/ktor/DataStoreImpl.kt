package com.jp.chatapp.data.repoImpl.ktor

import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.learning.dataStore.DataStorePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

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