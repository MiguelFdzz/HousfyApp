package fdz.migue.housfyapp.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fdz.migue.housfyapp.dao.chat.ChatMessage
import fdz.migue.housfyapp.dao.chat.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    val messages: StateFlow<List<ChatMessage>> =
        repository.getMessages()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun sendMessage(
        userName: String,
        userPhotoUrl: String?,
        message: String
    ) {
        if (message.isBlank()) return

        viewModelScope.launch {
            repository.sendMessage(
                ChatMessage(
                    userName = userName,
                    userPhotoUrl = userPhotoUrl,
                    message = message
                )
            )
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearChat()
        }
    }
}