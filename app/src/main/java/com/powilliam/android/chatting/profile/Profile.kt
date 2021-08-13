package com.powilliam.android.chatting.profile

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.powilliam.android.chatting.shared.ui.composables.ActionCard
import com.powilliam.android.chatting.shared.ui.composables.Description
import com.powilliam.android.chatting.shared.ui.viewmodels.AuthenticationState
import com.powilliam.android.chatting.shared.ui.viewmodels.AuthenticationViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel = getViewModel(),
    scrollState: ScrollState = rememberScrollState(),
    signOutFromGoogle: () -> Unit = {}
) {
    val authenticationState = authenticationViewModel.authenticationState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Go back to Chat Screen"
                        )
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier.scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
        ) {
            Crossfade(targetState = authenticationState.value) {
                when (it) {
                    is AuthenticationState.Authenticated -> WithAuthenticatedState(
                        account = it.account,
                        onClickSignOut = {
                            signOutFromGoogle()
                            navController.popBackStack()
                        })
                    else -> {
                    }
                }
            }
        }
    }
}

@Composable
private fun WithAuthenticatedState(account: FirebaseUser, onClickSignOut: () -> Unit = {}) =
    Column {
        Description(label = "DISPLAY NAME", text = account.displayName ?: "John Doe")
        Description(label = "E-MAIL", text = account.email ?: "john.doe@company.rs")
        Divider()
        ActionCard(
            trailing = {
                Icon(
                    imageVector = Icons.Rounded.Logout,
                    contentDescription = null
                )
            },
            label = "Logout",
            description = "You will be able to login again",
            onClick = onClickSignOut
        )
    }