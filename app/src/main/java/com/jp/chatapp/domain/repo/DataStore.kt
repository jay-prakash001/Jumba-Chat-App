package com.jp.chatapp.old

import com.jp.chatapp.old.domain.state.ResultState
import kotlinx.coroutines.flow.Flow

interface DataStore {
   suspend fun setToken(tokenName : String, tokenValue : String)
    fun getToken(tokenName : String): Flow<ResultState<String?>>
}
