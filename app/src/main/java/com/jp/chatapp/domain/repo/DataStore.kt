package com.jp.chatapp.domain.repo

import com.jp.chatapp.domain.state.ResultState
import kotlinx.coroutines.flow.Flow

interface DataStore {
   suspend fun setToken(tokenName : String, tokenValue : String)
    fun getToken(tokenName : String): Flow<ResultState<String?>>
    fun getTokenValue(tokenName : String): Flow<String?>
}
