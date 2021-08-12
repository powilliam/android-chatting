package com.powilliam.android.chatting

import android.app.Application
import com.powilliam.android.chatting.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChattingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChattingApplication)
            modules(mainModule)
        }
    }
}