package com.powilliam.android.chatting.utils

import kotlinx.datetime.*

fun now(timeZone: TimeZone = TimeZone.UTC): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

fun parse(
    isoString: String
): String {
    val parsedLocalDateTime = LocalDateTime.parse(isoString)
    return "${parsedLocalDateTime.dayOfMonth}/${parsedLocalDateTime.monthNumber}/${parsedLocalDateTime.year}"
}