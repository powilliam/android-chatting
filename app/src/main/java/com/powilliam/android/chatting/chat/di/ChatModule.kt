package com.powilliam.android.chatting.chat.di

import com.powilliam.android.chatting.chat.domain.repositories.ChatRepository
import com.powilliam.android.chatting.chat.domain.repositories.ChatRepositoryImpl
import com.powilliam.android.chatting.chat.ui.viewmodels.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    single<ChatRepository> { ChatRepositoryImpl(databaseRef = get(), firebaseCrashlytics = get()) }
    viewModel { ChatViewModel(chatRepository = get()) }
}