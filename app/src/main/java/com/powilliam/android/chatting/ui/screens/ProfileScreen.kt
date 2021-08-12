package com.powilliam.android.chatting.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.powilliam.android.chatting.ui.ChattingTheme
import com.powilliam.android.chatting.ui.composables.ActionCard
import com.powilliam.android.chatting.ui.composables.Description

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    scrollState: ScrollState = rememberScrollState(),
    signOutFromGoogle: () -> Unit = {}
) {
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
            Description(label = "DISPLAY NAME", text = "Lorem Ipsum")
            Description(label = "E-MAIL", text = "lorem.ipsum@company.rs")
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
                onClick = {
                    signOutFromGoogle()
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreen_Preview() {
    ChattingTheme {
        ProfileScreen(navController = rememberNavController())
    }
}