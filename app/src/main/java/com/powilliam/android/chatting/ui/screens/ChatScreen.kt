package com.powilliam.android.chatting.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.powilliam.android.chatting.Screen

@Composable
fun ChatScreen(navController: NavHostController) {
    Surface(color = MaterialTheme.colors.background) {
        TextButton(onClick = { navController.navigate(route = Screen.Profile.route) }) {
            Text(
                text = Screen.Chat.route,
                style = MaterialTheme.typography.body1
            )
        }
    }
}