package com.jp.chatapp.domain.module

import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.jp.chatapp.data.repoImpl.network.APIDataRepoImpl
import com.jp.chatapp.data.repoImpl.network.SocketManagerImpl
import com.jp.chatapp.data.repoImpl.dataStore.DataStoreImpl
import com.jp.chatapp.data.websocket.WebSocketManager
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.data.dataStore.DataStorePref
import com.jp.chatapp.data.repoImpl.room.LocalRoomDbImpl
import com.jp.chatapp.data.room.ChatDb
import com.jp.chatapp.data.room.entities.Chat
import com.jp.chatapp.domain.repo.LocalRoomDbRepo
import com.jp.chatapp.domain.repo.SocketManagerRepo
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.koinInject
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.koinApplication
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
    single {
        Room.databaseBuilder(context = androidContext(), ChatDb::class.java, "chat_db").build()
    }
//    single {
//        fun provideLocalDataRepo(db: ChatDb) : LocalRoomDbRepo{
//            return LocalRoomDbImpl(db.dao)
//        }
//    }
    single<LocalRoomDbRepo>{
        val db : ChatDb = get()
        LocalRoomDbImpl(db.dao)
    }
    viewModel {
        LoginViewmodel(get(), get())
    }
    viewModel {
        ChatViewModel(get(), get(), get())
    }
    viewModel {
        MainViewmodel(get(), get())
    }
    viewModel {
        HomeViewModel(get(), get(), get(),get())
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