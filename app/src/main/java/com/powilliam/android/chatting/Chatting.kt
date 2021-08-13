package com.powilliam.android.chatting

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.android.chatting.chat.Chat
import com.powilliam.android.chatting.chat.ui.viewmodels.ChatViewModel
import com.powilliam.android.chatting.profile.Profile
import com.powilliam.android.chatting.shared.ui.viewmodels.AuthenticationViewModel
import com.powilliam.android.chatting.ui.ChattingTheme
import org.koin.androidx.compose.getViewModel

sealed class Screen(val route: String) {
    object Chat : Screen(route = "chat")
    object Profile : Screen(route = "profile")
}

@Composable
fun Chatting(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = getViewModel(),
    chatViewModel: ChatViewModel = getViewModel(),
    signInWithGoogle: () -> Unit = {},
    signOutFromGoogle: () -> Unit = {}
) = ChattingTheme {
    NavHost(navController = navController, startDestination = Screen.Chat.route) {
        composable(route = Screen.Chat.route) {
            Chat(
                navController = navController,
                authenticationViewModel = authenticationViewModel,
                chatViewModel = chatViewModel,
                signInWithGoogle = { signInWithGoogle() })
        }
        composable(route = Screen.Profile.route) {
            Profile(
                navController = navController,
                authenticationViewModel = authenticationViewModel,
                signOutFromGoogle = { signOutFromGoogle() })
        }
    }
}