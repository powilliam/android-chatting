package com.powilliam.android.chatting.shared.di

import com.powilliam.android.chatting.shared.ui.viewmodels.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sharedModule = module {
    viewModel { AuthenticationViewModel() }
}