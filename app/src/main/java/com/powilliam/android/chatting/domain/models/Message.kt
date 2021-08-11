package com.powilliam.android.chatting.domain.models

import java.util.*

data class Message(
    val uid: UUID = UUID.randomUUID(),
    val avatarUrl: String = "",
    val displayName: String,
    val date: String = "21/09/21",
    val content: String = ""
)