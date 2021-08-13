package com.powilliam.android.chatting

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.powilliam.android.chatting.chat.di.chatModule
import com.powilliam.android.chatting.di.mainModule
import com.powilliam.android.chatting.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
        startKoin {
            androidContext(this@MainApplication)
            modules(mainModule, chatModule, sharedModule)
        }
    }
}