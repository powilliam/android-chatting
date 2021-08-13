package com.powilliam.android.chatting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.powilliam.android.chatting.domain.models.Message
import com.powilliam.android.chatting.domain.repositories.MessagesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class MessagesState(val content: String = "", val messages: List<Message> = listOf())

class MessagesViewModel(private val messagesRepository: MessagesRepository) : ViewModel() {
    private var _messagesState: MutableStateFlow<MessagesState> = MutableStateFlow(MessagesState())
    val messagesState: StateFlow<MessagesState> = _messagesState

    init {
        viewModelScope.launch {
            messagesRepository.realtimeMessages.collect { messages ->
                _messagesState.emit(
                    _messagesState.value.copy(messages = messages)
                )
            }
        }
    }

    fun onContentChanged(newContent: String) = viewModelScope.launch {
        _messagesState.emit(_messagesState.value.copy(content = newContent))
    }

    fun onCreateMessage(account: FirebaseUser) = viewModelScope.launch {
        if (_messagesState.value.content.isNotEmpty()) {
            val message = Message(
                content = _messagesState.value.content,
                displayName = account.displayName ?: "John Doe",
                avatarUrl = account.photoUrl.toString()
            )
            messagesRepository.create(message)
            _messagesState.emit(_messagesState.value.copy(content = ""))
        }
    }

    override fun onCleared() {
        super.onCleared()
        messagesRepository.close()
    }
}