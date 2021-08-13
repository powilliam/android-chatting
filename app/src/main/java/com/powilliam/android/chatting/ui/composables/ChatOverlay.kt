package com.powilliam.android.chatting.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
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
    object DisplayProgressIndicator : ChatOverlayState()
    object DisplayMessageForm : ChatOverlayState()
    object DisplayGoogleSignIn : ChatOverlayState()
}

@Composable
fun ChatOverlay(
    modifier: Modifier = Modifier,
    chatOverlayState: ChatOverlayState = ChatOverlayState.DisplayGoogleSignIn,
    content: String = "",
    onContentChanged: (String) -> Unit = {},
    onCreateMessage: () -> Unit = {},
    onPressGoogleSignIn: () -> Unit = {}
) = Surface(
    color = MaterialTheme.colors.background,
    modifier = modifier
        .fillMaxWidth()
) {
    Crossfade(targetState = chatOverlayState) { state ->
        when (state) {
            is ChatOverlayState.DisplayProgressIndicator -> WithProgressIndicator()
            is ChatOverlayState.DisplayGoogleSignIn -> WithGoogleSignIn {
                onPressGoogleSignIn()
            }
            is ChatOverlayState.DisplayMessageForm -> WithMessageForm(
                content = content,
                onContentChanged = onContentChanged,
                onCreateMessage = onCreateMessage
            )
        }
    }
}

@Composable
private fun WithProgressIndicator() = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp), horizontalArrangement = Arrangement.Center
) {
    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 1.dp)
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
private fun WithMessageForm(
    content: String = "",
    onContentChanged: (String) -> Unit,
    onCreateMessage: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        val (textField, submit) = createRefs()

        TextField(
            value = content,
            onValueChange = onContentChanged,
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
            onClick = onCreateMessage,
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