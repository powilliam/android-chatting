package com.powilliam.android.chatting.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.powilliam.android.chatting.ui.ChattingTheme

sealed class ChatAppBarState {
    object Hidden : ChatAppBarState()
    data class Visible(val onPressActionButton: () -> Unit = {}) : ChatAppBarState()
}

@Composable
fun ChatAppBar(appBarState: ChatAppBarState = ChatAppBarState.Hidden) =
    Crossfade(targetState = appBarState) { state ->
        when (state) {
            is ChatAppBarState.Visible -> VisibleAppBar {
                state.onPressActionButton()
            }
            else -> {
            }
        }
    }

@Composable
private fun VisibleAppBar(onPressActionButton: () -> Unit) = TopAppBar(
    elevation = 0.dp,
    backgroundColor = MaterialTheme.colors.background,
    title = {},
    actions = {
        IconButton(onClick = onPressActionButton) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = "Go to Profile Screen"
            )
        }
    })

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
        ChatAppBar(appBarState = ChatAppBarState.Visible())
    }
}