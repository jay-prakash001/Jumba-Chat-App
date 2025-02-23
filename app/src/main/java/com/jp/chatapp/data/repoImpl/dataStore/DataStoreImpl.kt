package com.jp.chatapp.data.repoImpl.dataStore

import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.data.dataStore.DataStorePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DataStoreImpl(private val dataStorePref: DataStorePref) : DataStore {
    override suspend fun setToken(tokenName: String, tokenValue: String?) {
        dataStorePref.setToken(tokenName, tokenValue)
    }

     fun getToken0(tokenName: String): Flow<ResultState<String?>> = callbackFlow {
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


    override fun getToken(tokenName: String): Flow<ResultState<String?>> =
        dataStorePref.getToken(tokenName)
            .map { token ->
                if (token.isNullOrEmpty()) {
                    ResultState.Error("Token not found")
                } else {
                    ResultState.Success(token)
                }
            }
            .onStart { emit(ResultState.Loading) }
            .catch { e -> emit(ResultState.Error(e.localizedMessage ?: "Unknown error")) }

    override fun getTokenValue(tokenName: String): Flow<String?>  = dataStorePref.getToken(tokenName)


}