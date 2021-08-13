package com.powilliam.android.chatting.ui.screens

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
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

sealed class ScrollState {
    object Parked : ScrollState()
    object Scrolling : ScrollState()
}

@Composable
fun ChatScreen(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel = getViewModel(),
    messagesViewModel: MessagesViewModel = getViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    signInWithGoogle: () -> Unit = {}
) {
    val authenticationState = authenticationViewModel.authenticationState.collectAsState()
    val messagesState = messagesViewModel.messagesState.collectAsState()

    val listState = rememberLazyListState()

    val scrollState by remember {
        derivedStateOf {
            if (!listState.isScrollInProgress) {
                ScrollState.Parked
            } else {
                ScrollState.Scrolling
            }
        }
    }
    val appBarState by remember {
        derivedStateOf {
            when (authenticationState.value) {
                is AuthenticationState.Authenticated -> ChatAppBarState.Visible
                else -> ChatAppBarState.Hidden
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
            ChatAppBar(
                appBarState = appBarState,
                scrollState = scrollState,
                onPressProfileActionButton = {
                    navController.navigate(
                        Screen.Profile.route
                    )
                })
        },
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (lazyColumn, divider, actions) = createRefs()

            MessageCardList(
                listState = listState,
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
                onTextInputIsFocused = {
                    coroutineScope.launch {
                        listState.animateScrollBy(
                            value = listState.layoutInfo.viewportEndOffset.toFloat(),
                        )
                    }
                },
                modifier = Modifier.constrainAs(ref = actions) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = parent.bottom)
                })
        }
    }
}