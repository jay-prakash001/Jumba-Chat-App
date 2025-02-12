package com.jp.chatapp.domain.module

import android.system.Os.bind
import androidx.datastore.preferences.preferencesDataStore
import com.jp.chatapp.data.repoImpl.ktor.APIDataRepoImpl
import com.jp.chatapp.data.repoImpl.ktor.SocketManagerImpl
import com.jp.chatapp.data.repoImpl.ktor.DataStoreImpl
import com.jp.chatapp.data.websocket.WebSocketManager
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.data.dataStore.DataStorePref
import com.jp.chatapp.domain.repo.SocketManagerRepo
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {


    single { DataStorePref(androidContext()) }

//    singleOf(::WebSocketManager) { bind<WebSocketManager>() }
    single {
        WebSocketManager
    }
    single {
        preferencesDataStore("token")
    }

    viewModel {
        LoginViewmodel(get(), get())
    }
    viewModel {
        ChatViewModel(get(), get())
    }
    viewModel {
        MainViewmodel(get(), get())
    }
    viewModel {
        HomeViewModel(get(), get(), get())
    }
    singleOf(::APIDataRepoImpl) {
        bind<APIDataRepo>()
    }

    singleOf(::DataStoreImpl) {
        bind<DataStore>()
    }

    singleOf(::SocketManagerImpl) {
        bind<SocketManagerRepo>()
    }


}