package com.powilliam.android.chatting.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.powilliam.android.chatting.Screen

@Composable
fun ProfileScreen(navController: NavHostController) {
    Surface(color = MaterialTheme.colors.background) {
        Text(
            text = Screen.Profile.route,
            style = MaterialTheme.typography.body1
        )
    }
}