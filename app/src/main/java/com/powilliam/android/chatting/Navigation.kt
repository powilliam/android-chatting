package com.powilliam.android.chatting

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.android.chatting.ui.screens.ChatScreen
import com.powilliam.android.chatting.ui.screens.ProfileScreen

sealed class Screen(val route: String) {
    object Chat : Screen(route = "chat")
    object Profile : Screen(route = "profile")
}

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Chat.route) {
        composable(route = Screen.Chat.route) {
            ChatScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}