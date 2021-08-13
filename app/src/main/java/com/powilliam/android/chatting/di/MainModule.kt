package com.powilliam.android.chatting.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val mainModule = module {
    single { Firebase.database.reference }
    single { FirebaseCrashlytics.getInstance() }
}