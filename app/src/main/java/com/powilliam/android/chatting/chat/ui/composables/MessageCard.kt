package com.powilliam.android.chatting.chat.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.powilliam.android.chatting.chat.domain.models.Message
import com.powilliam.android.chatting.ui.ChattingTheme
import com.powilliam.android.chatting.utils.parse
import com.powilliam.android.chatting.R

@Composable
fun MessageCard(message: Message) = Surface(
    color = MaterialTheme.colors.background,
    shape = MaterialTheme.shapes.medium
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (avatar, displayName, createdAt, content) = createRefs()

        Image(
            painter = rememberImagePainter(
                data = message.avatarUrl,
                builder = {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(drawableResId = R.drawable.ic_round_account_circle_24)
                },
            ),
            contentDescription = "${message.displayName} avatar",
            modifier = Modifier
                .constrainAs(ref = avatar) {
                    start.linkTo(anchor = parent.start, margin = 8.dp)
                    top.linkTo(anchor = parent.top, margin = 12.dp)
                }
                .size(38.dp)
        )

        Text(
            text = message.displayName,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(ref = displayName) {
                top.linkTo(anchor = avatar.top)
                start.linkTo(anchor = avatar.end, margin = 12.dp)
                end.linkTo(anchor = parent.end, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = parse(message.date),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(ref = createdAt) {
                    top.linkTo(anchor = displayName.bottom)
                    start.linkTo(anchor = displayName.start)
                    end.linkTo(anchor = displayName.end)
                    width = Dimension.fillToConstraints
                }
            )
        }

        Text(
            text = message.content,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(ref = content) {
                top.linkTo(anchor = avatar.bottom, margin = 12.dp)
                start.linkTo(anchor = displayName.start)
                end.linkTo(anchor = displayName.end)
                bottom.linkTo(anchor = parent.bottom, margin = 12.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MessageCard_Preview() {
    ChattingTheme {
        MessageCard(message = Message(displayName = "William Porto", content = "Lorem Ipsum"))
    }
}