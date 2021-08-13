package com.powilliam.android.chatting.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.powilliam.android.chatting.domain.repositories.MessagesRepository
import com.powilliam.android.chatting.domain.repositories.MessagesRepositoryImpl
import com.powilliam.android.chatting.ui.viewmodels.AuthenticationViewModel
import com.powilliam.android.chatting.ui.viewmodels.MessagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { Firebase.database.reference }
    single { FirebaseCrashlytics.getInstance() }

    single<MessagesRepository> {
        MessagesRepositoryImpl(
            databaseRef = get(),
            firebaseCrashlytics = get()
        )
    }

    viewModel { AuthenticationViewModel() }
    viewModel { MessagesViewModel(messagesRepository = get()) }
}