package com.powilliam.android.chatting.chat.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.powilliam.android.chatting.shared.domain.models.Message
import com.powilliam.android.chatting.ui.ChattingTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageCardList(
    modifier: Modifier = Modifier,
    messages: List<Message> = listOf(),
    listState: LazyListState = rememberLazyListState()
) {
    LaunchedEffect(messages, listState) {
        messages.lastOrNull()?.let { message ->
            listState.animateScrollToItem(messages.indexOf(message))
        }
    }

    LazyColumn(modifier, state = listState) {
        items(items = messages, key = { it.uid }) {
            MessageCard(message = it)
        }
    }
}

@Composable
private fun MessageCardStickyHeader() = Text(
    text = "Sunday, August 8th",
    style = MaterialTheme.typography.caption.copy(
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Bold
    ),
    modifier = Modifier.padding(start = 8.dp, top = 16.dp)
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MessageCardList_Preview() {
    ChattingTheme {
        MessageCardList(
            messages = listOf(
                Message(
                    displayName = "William Porto",
                    content = "AAAAAAAAA"
                ),
                Message(
                    displayName = "William Porto",
                    content = "BBBBBBBBBB"
                ),
            )
        )
    }
}