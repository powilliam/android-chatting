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
import org.koin.androidx.compose.getViewModel

@Composable
fun ChatScreen(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel = getViewModel(),
    signInWithGoogle: () -> Unit = {}
) {
    val authenticationState = authenticationViewModel.authenticationState.collectAsState()
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
            if (authenticationState.value is AuthenticationState.Unauthenticated) {
                ChatOverlayState.DisplayGoogleSignIn(onPressGoogleSignIn = { signInWithGoogle() })
            } else {
                ChatOverlayState.DisplayMessageForm
            }
        }
    }

    Scaffold(
        topBar = {
            ChatAppBar(appBarState = appBarState)
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (lazyColumn, divider, actions) = createRefs()

            MessageCardList(modifier = Modifier.constrainAs(ref = lazyColumn) {
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
                modifier = Modifier.constrainAs(ref = actions) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = parent.bottom)
                })
        }
    }
}