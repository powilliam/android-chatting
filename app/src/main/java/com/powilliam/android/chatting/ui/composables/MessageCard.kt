package com.powilliam.android.chatting.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.powilliam.android.chatting.ui.ChattingTheme

@Composable
fun MessageCard() = Surface(
    color = MaterialTheme.colors.background,
    shape = MaterialTheme.shapes.medium
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (avatar, displayName, createdAt, content) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(ref = avatar) {
                    start.linkTo(anchor = parent.start, margin = 8.dp)
                    top.linkTo(anchor = parent.top, margin = 12.dp)
                }
                .size(38.dp)
                .background(color = MaterialTheme.colors.surface)
        )

        Text(
            text = "Fulaninho",
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
                text = "12 min ago",
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
            text = "Lorem ipsum dolor asimetfodase",
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
        MessageCard()
    }
}