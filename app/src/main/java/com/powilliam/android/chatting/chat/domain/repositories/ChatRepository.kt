package com.powilliam.android.chatting.chat.domain.repositories

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.powilliam.android.chatting.shared.domain.models.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.io.Closeable

interface ChatRepository : ValueEventListener, Closeable {
    val realtimeMessages: StateFlow<List<Message>>

    suspend fun writeMessages(vararg messages: Message)
}

class ChatRepositoryImpl(
    private val databaseRef: DatabaseReference,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ChatRepository {
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    private var _realtimeMessages: MutableStateFlow<List<Message>> = MutableStateFlow(listOf())
    override val realtimeMessages: StateFlow<List<Message>> = _realtimeMessages

    init {
        databaseRef.child(DATABASE_CHILD).keepSynced(true)
        databaseRef.child(DATABASE_CHILD).addValueEventListener(this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        coroutineScope.launch {
            snapshot.getValue<Map<String, Message>>()?.let { mapOfMessages ->
                val messages = mapOfMessages.map { (_, message) -> message }.sortedBy { message ->
                    message.date.toLocalDateTime().toInstant(timeZone = TimeZone.UTC)
                        .toEpochMilliseconds()
                }
                _realtimeMessages.emit(messages)
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        firebaseCrashlytics.recordException(error.toException())
    }

    override suspend fun writeMessages(vararg messages: Message) {
        coroutineScope.launch {
            val updates: MutableMap<String, Any> = HashMap()
            val inserts = messages.map { message -> Pair(message.uid, message) }
            updates.putAll(inserts)
            databaseRef.child(DATABASE_CHILD).updateChildren(updates)
        }
    }

    override fun close() {
        databaseRef.child(DATABASE_CHILD).removeEventListener(this)
    }

    companion object {
        private const val DATABASE_CHILD = "messages"
    }
}