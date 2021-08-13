package com.powilliam.android.chatting.shared.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthenticationState {
    object Unauthenticated : AuthenticationState()
    object Authenticating : AuthenticationState()
    data class AuthenticationFailed(val reason: String) : AuthenticationState()
    data class Authenticated(val account: FirebaseUser) : AuthenticationState()
}

class AuthenticationViewModel : ViewModel() {
    private var _authenticationState: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Unauthenticated)
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState

    fun authenticating() = viewModelScope.launch {
        _authenticationState.emit(AuthenticationState.Authenticating)
    }

    fun authenticate(account: FirebaseUser) = viewModelScope.launch {
        _authenticationState.emit(AuthenticationState.Authenticated(account))
    }

    fun unAuthenticate() = viewModelScope.launch {
        _authenticationState.emit(AuthenticationState.Unauthenticated)
    }

    fun authenticationFailed(reason: String = DEFAULT_AUTHENTICATION_FAILURE_REASON) =
        viewModelScope.launch {
            _authenticationState.emit(AuthenticationState.AuthenticationFailed(reason))
        }

    companion object {
        private const val DEFAULT_AUTHENTICATION_FAILURE_REASON =
            "Failed to authenticate with Google Credentials"
    }
}