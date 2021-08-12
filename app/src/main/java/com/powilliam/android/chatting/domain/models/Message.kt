package com.powilliam.android.chatting.domain.models

import com.powilliam.android.chatting.utils.now
import java.util.*

data class Message(
    val uid: String = UUID.randomUUID().toString(),
    val avatarUrl: String = "",
    val displayName: String = "",
    val date: String = now().toString(),
    val content: String = ""
)