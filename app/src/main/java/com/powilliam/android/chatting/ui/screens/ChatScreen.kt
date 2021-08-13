package com.powilliam.android.chatting.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.powilliam.android.chatting.Screen
import com.powilliam.android.chatting.ui.composables.*
import com.powilliam.android.chatting.ui.viewmodels.AuthenticationState
import com.powilliam.android.chatting.ui.viewmodels.AuthenticationViewModel
import com.powilliam.android.chatting.ui.viewmodels.MessagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun ChatScreen(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel = getViewModel(),
    messagesViewModel: MessagesViewModel = getViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    signInWithGoogle: () -> Unit = {}
) {
    val authenticationState = authenticationViewModel.authenticationState.collectAsState()
    val messagesState = messagesViewModel.messagesState.collectAsState()

    val appBarState by remember {
        derivedStateOf {
            if (authenticationState.value is AuthenticationState.Unauthenticated) {
                ChatAppBarState.Hidden
            } else {
                ChatAppBarState.Visible(onPressActionButton = { navController.navigate(Screen.Profile.route) })
            }
        }
    }
    val chatOverlayState by remember {
        derivedStateOf {
            when (authenticationState.value) {
                is AuthenticationState.Authenticating -> ChatOverlayState.DisplayProgressIndicator
                is AuthenticationState.Authenticated -> ChatOverlayState.DisplayMessageForm
                is AuthenticationState.Unauthenticated -> ChatOverlayState.DisplayGoogleSignIn
                is AuthenticationState.AuthenticationFailed -> ChatOverlayState.DisplayGoogleSignIn
            }
        }
    }

    if (authenticationState.value is AuthenticationState.AuthenticationFailed) {
        LaunchedEffect(authenticationState, scaffoldState) {
            launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    (authenticationState.value as AuthenticationState.AuthenticationFailed).reason,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ChatAppBar(appBarState = appBarState)
        },
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (lazyColumn, divider, actions) = createRefs()

            MessageCardList(
                messages = messagesState.value.messages,
                modifier = Modifier.constrainAs(ref = lazyColumn) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = divider.top)
                    height = Dimension.fillToConstraints
                })

            Divider(modifier = Modifier.constrainAs(ref = divider) {
                start.linkTo(anchor = parent.start)
                end.linkTo(anchor = parent.end)
                bottom.linkTo(anchor = actions.top)
            })


            ChatOverlay(
                chatOverlayState = chatOverlayState,
                content = messagesState.value.content,
                onContentChanged = { newContent -> messagesViewModel.onContentChanged(newContent) },
                onCreateMessage = {
                    if (authenticationState.value is AuthenticationState.Authenticated) {
                        messagesViewModel.onCreateMessage((authenticationState.value as AuthenticationState.Authenticated).account)
                    }
                },
                onPressGoogleSignIn = { signInWithGoogle() },
                modifier = Modifier.constrainAs(ref = actions) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = parent.bottom)
                })
        }
    }
}