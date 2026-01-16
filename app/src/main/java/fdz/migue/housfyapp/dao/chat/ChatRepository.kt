package fdz.migue.housfyapp.dao.chat

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessages(): Flow<List<ChatMessage>>
    suspend fun sendMessage(message: ChatMessage)
    suspend fun clearChat()
}

class ChatRepositoryImpl(
    private val dao: ChatDAO
) : ChatRepository {

    override fun getMessages(): Flow<List<ChatMessage>> {
        return dao.getAllMessages()
    }

    override suspend fun sendMessage(message: ChatMessage) {
        dao.insertMessage(message)
    }

    override suspend fun clearChat() {
        dao.deleteAllMessages()
    }
}