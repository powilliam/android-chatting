package com.powilliam.android.chatting.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.powilliam.android.chatting.ui.ChattingTheme

sealed class ChatOverlayState {
    data class DisplayGoogleSignIn(val onPressGoogleSignIn: () -> Unit = {}) : ChatOverlayState()
    object DisplayMessageForm : ChatOverlayState()
}

@Composable
fun ChatOverlay(
    modifier: Modifier = Modifier,
    chatOverlayState: ChatOverlayState = ChatOverlayState.DisplayGoogleSignIn()
) = Surface(
    color = MaterialTheme.colors.background,
    modifier = modifier
        .fillMaxWidth()
) {
    Crossfade(targetState = chatOverlayState) { state ->
        when (state) {
            is ChatOverlayState.DisplayGoogleSignIn -> WithGoogleSignIn {
                state.onPressGoogleSignIn()
            }
            is ChatOverlayState.DisplayMessageForm -> WithMessageForm()
        }
    }
}

@Composable
private fun WithGoogleSignIn(onPressGoogleSignIn: () -> Unit = {}) =
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onPressGoogleSignIn) {
            Text(
                text = "Sign in with Google",
                style = MaterialTheme.typography.button
            )
        }
    }

@Composable
private fun WithMessageForm() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        val (textField, submit) = createRefs()

        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Message")
            },
            shape = RoundedCornerShape(128.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .constrainAs(ref = textField) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = submit.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(ref = submit) {
                end.linkTo(anchor = parent.end)
                top.linkTo(anchor = parent.top)
                bottom.linkTo(anchor = parent.bottom)
            }) {
            Icon(imageVector = Icons.Rounded.Send, contentDescription = "Send message")
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChatOverlayWithGoogleSignInState_Preview() {
    ChattingTheme {
        ChatOverlay()
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChatOverlayWithMessageFormState_Preview() {
    ChattingTheme {
        ChatOverlay(chatOverlayState = ChatOverlayState.DisplayMessageForm)
    }
}