package com.powilliam.android.chatting.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.android.chatting.Screen
import com.powilliam.android.chatting.ui.ChattingTheme
import com.powilliam.android.chatting.ui.composables.ChatAppBar
import com.powilliam.android.chatting.ui.composables.ChatOverlay
import com.powilliam.android.chatting.ui.composables.MessageCard
import com.powilliam.android.chatting.ui.composables.MessageCardList

@Composable
fun ChatScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ChatAppBar()
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


            ChatOverlay(modifier = Modifier.constrainAs(ref = actions) {
                start.linkTo(anchor = parent.start)
                end.linkTo(anchor = parent.end)
                bottom.linkTo(anchor = parent.bottom)
            })
        }
    }
}


@Preview(name = "Light ChatScreen")
@Preview(name = "Dark ChatScreen", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChatScreen_Preview() {
    ChattingTheme {
        ChatScreen(navController = rememberNavController())
    }
}