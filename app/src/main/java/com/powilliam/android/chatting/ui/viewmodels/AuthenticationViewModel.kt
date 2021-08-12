package com.powilliam.android.chatting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthenticationState {
    object Unauthenticated : AuthenticationState()
    data class Authenticated(val account: FirebaseUser) : AuthenticationState()
}

class AuthenticationViewModel : ViewModel() {
    private var _authenticationState: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Unauthenticated)
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState

    fun authenticate(account: FirebaseUser) = viewModelScope.launch {
        _authenticationState.emit(AuthenticationState.Authenticated(account))
    }

    fun unAuthenticate() = viewModelScope.launch {
        _authenticationState.emit(AuthenticationState.Unauthenticated)
    }
}