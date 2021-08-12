package com.powilliam.android.chatting

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.android.chatting.ui.screens.ChatScreen
import com.powilliam.android.chatting.ui.screens.ProfileScreen
import com.powilliam.android.chatting.ui.viewmodels.AuthenticationViewModel
import com.powilliam.android.chatting.ui.viewmodels.MessagesViewModel
import org.koin.androidx.compose.getViewModel

sealed class Screen(val route: String) {
    object Chat : Screen(route = "chat")
    object Profile : Screen(route = "profile")
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = getViewModel(),
    messagesViewModel: MessagesViewModel = getViewModel(),
    signInWithGoogle: () -> Unit = {},
    signOutFromGoogle: () -> Unit = {}
) {
    NavHost(navController = navController, startDestination = Screen.Chat.route) {
        composable(route = Screen.Chat.route) {
            ChatScreen(
                navController = navController,
                authenticationViewModel = authenticationViewModel,
                signInWithGoogle = { signInWithGoogle() })
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                authenticationViewModel = authenticationViewModel,
                signOutFromGoogle = { signOutFromGoogle() })
        }
    }
}