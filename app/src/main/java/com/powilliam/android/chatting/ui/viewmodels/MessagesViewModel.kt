package com.powilliam.android.chatting.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.powilliam.android.chatting.domain.models.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

data class MessagesState(val content: String = "", val messages: List<Message> = listOf())

class MessagesViewModel(private val database: DatabaseReference) : ViewModel() {
    private var _messagesState: MutableStateFlow<MessagesState> = MutableStateFlow(MessagesState())
    val messagesState: StateFlow<MessagesState> = _messagesState

    init {
        val messagesValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                viewModelScope.launch {
                    snapshot.getValue<Map<String, Message>>()?.let { mapOfMessages ->
                        val messages = mapOfMessages.map { (_, message) -> message }
                            .sortedBy {
                                it.date.toLocalDateTime()
                                    .toInstant(timeZone = TimeZone.UTC)
                                    .toEpochMilliseconds()
                            }
                        _messagesState.emit(_messagesState.value.copy(messages = messages))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MessagesViewModel", error.message)
            }
        }
        database.child("messages").addValueEventListener(messagesValueEventListener)
    }

    fun onContentChanged(newContent: String) = viewModelScope.launch {
        _messagesState.emit(_messagesState.value.copy(content = newContent))
    }

    fun onCreateMessage(displayName: String) = viewModelScope.launch {
        if (_messagesState.value.content.isNotEmpty()) {
            val message = Message(content = _messagesState.value.content, displayName = displayName)
            database.child("messages").child(message.uid).setValue(message)
            _messagesState.emit(_messagesState.value.copy(content = ""))
        }
    }
}