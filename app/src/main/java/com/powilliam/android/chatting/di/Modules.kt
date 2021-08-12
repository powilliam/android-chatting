package com.powilliam.android.chatting.di

import com.powilliam.android.chatting.ui.viewmodels.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { AuthenticationViewModel() }
}