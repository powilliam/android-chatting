package com.powilliam.android.chatting.chat.domain.repositories

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.powilliam.android.chatting.chat.domain.models.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Closeable

interface ChatRepository : ChildEventListener, Closeable {
    val messages: StateFlow<List<Message>>

    suspend fun writeMessages(vararg messages: Message)
}

class ChatRepositoryImpl(
    private val databaseRef: DatabaseReference,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ChatRepository {
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    private var _messages: MutableStateFlow<List<Message>> = MutableStateFlow(listOf())
    override val messages: StateFlow<List<Message>> = _messages

    init {
        databaseRef.child(DATABASE_CHILD).also { messages ->
            messages.keepSynced(true)
            messages.orderByChild("date").addChildEventListener(this)
        }
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        coroutineScope.launch {
            snapshot.getValue<Message>()?.let { message ->
                val newMessages = _messages.value.toMutableList()
                    .also { messages -> messages.add(message) }
                _messages.emit(newMessages)
            }
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

    override fun onChildRemoved(snapshot: DataSnapshot) {
        coroutineScope.launch {
            snapshot.getValue<Message>()?.let { message ->
                val newMessages = _messages.value.toMutableList()
                    .also { messages -> messages.remove(message) }
                _messages.emit(newMessages)
            }
        }
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

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