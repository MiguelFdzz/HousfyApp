package fdz.migue.housfyapp.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fdz.migue.housfyapp.dao.chat.ChatRepository

class ChatViewModelFactory(
    private val repository: ChatRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}