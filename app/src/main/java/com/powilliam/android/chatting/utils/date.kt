package com.powilliam.android.chatting.utils

import kotlinx.datetime.*

fun now(): Long {
    return Clock.System.now().toEpochMilliseconds()
}

fun parse(
    epochMilliseconds: Long,
    timeZone: TimeZone = TimeZone.UTC
): String {
    val parsedLocalDateTime =
        Instant.fromEpochMilliseconds(epochMilliseconds).toLocalDateTime(timeZone)
    return "${parsedLocalDateTime.dayOfMonth}/${parsedLocalDateTime.monthNumber}/${parsedLocalDateTime.year}"
}