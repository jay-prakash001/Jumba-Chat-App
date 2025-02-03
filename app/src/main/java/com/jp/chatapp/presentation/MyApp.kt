package com.jp.chatapp.presentation

import android.app.Application
import com.jp.chatapp.domain.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule)
        }
    }

}