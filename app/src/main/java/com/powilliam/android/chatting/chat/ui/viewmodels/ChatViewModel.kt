package com.powilliam.android.chatting.chat.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.powilliam.android.chatting.shared.domain.models.Message
import com.powilliam.android.chatting.chat.domain.repositories.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class ChatState(val content: String = "", val messages: List<Message> = listOf())

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {
    private var _chatState: MutableStateFlow<ChatState> = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState

    init {
        viewModelScope.launch {
            chatRepository.realtimeMessages.collect { messages ->
                _chatState.emit(
                    _chatState.value.copy(messages = messages)
                )
            }
        }
    }

    fun onContentChanged(newContent: String) = viewModelScope.launch {
        _chatState.emit(_chatState.value.copy(content = newContent))
    }

    fun onCreateMessage(account: FirebaseUser) = viewModelScope.launch {
        if (_chatState.value.content.isNotEmpty()) {
            val message = Message(
                content = _chatState.value.content,
                displayName = account.displayName ?: "John Doe",
                avatarUrl = account.photoUrl.toString()
            )
            chatRepository.writeMessages(message)
            _chatState.emit(_chatState.value.copy(content = ""))
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatRepository.close()
    }
}