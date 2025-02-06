package com.jp.chatapp.old.domain.module

import androidx.datastore.preferences.preferencesDataStore
import com.jp.chatapp.old.data.repoImpl.ktor.APIDataRepoImpl
import com.jp.chatapp.old.data.repoImpl.ktor.DataStoreImpl
import com.jp.chatapp.old.data.websocket.WebSocketManager
import com.jp.chatapp.old.APIDataRepo
import com.jp.chatapp.old.DataStore
import com.jp.chatapp.old.dataStore.DataStorePref
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

    singleOf(::WebSocketManager) { bind<WebSocketManager>() }
    single {
        preferencesDataStore("token")
    }
    viewModel {
        LoginViewmodel(get(), get())
    }
    viewModel {
        ChatViewModel(get())
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


}