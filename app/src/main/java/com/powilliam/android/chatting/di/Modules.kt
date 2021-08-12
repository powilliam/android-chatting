package com.powilliam.android.chatting.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.powilliam.android.chatting.ui.viewmodels.AuthenticationViewModel
import com.powilliam.android.chatting.ui.viewmodels.MessagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { Firebase.database.reference }

    viewModel { AuthenticationViewModel() }
    viewModel { MessagesViewModel(databaseRef = get()) }
}