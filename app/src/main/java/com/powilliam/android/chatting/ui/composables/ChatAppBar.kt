package com.powilliam.android.chatting.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.powilliam.android.chatting.ui.ChattingTheme
import com.powilliam.android.chatting.ui.screens.ScrollState

sealed class ChatAppBarState {
    object Hidden : ChatAppBarState()
    object Visible : ChatAppBarState()
}

@Composable
fun ChatAppBar(
    appBarState: ChatAppBarState = ChatAppBarState.Hidden,
    scrollState: ScrollState = ScrollState.Parked,
    onPressProfileActionButton: () -> Unit = {}
) =
    Crossfade(targetState = appBarState) { state ->
        when (state) {
            is ChatAppBarState.Visible -> VisibleAppBar(
                scrollState = scrollState,
                onPressProfileActionButton = {
                    onPressProfileActionButton()
                })
            is ChatAppBarState.Hidden -> {
            }
        }
    }

@Composable
private fun VisibleAppBar(
    scrollState: ScrollState = ScrollState.Parked,
    onPressProfileActionButton: () -> Unit = {}
) {
    val elevation by animateDpAsState(
        targetValue = if (scrollState is ScrollState.Parked) 0.dp else 6.dp,
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )
    TopAppBar(
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.background,
        title = {},
        actions = {
            IconButton(onClick = onPressProfileActionButton) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Go to Profile Screen"
                )
            }
        })
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChatAppBarHiddenState_Preview() {
    ChattingTheme {
        ChatAppBar()
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChatAppBarVisibleState_Preview() {
    ChattingTheme {
        ChatAppBar(appBarState = ChatAppBarState.Visible)
    }
}