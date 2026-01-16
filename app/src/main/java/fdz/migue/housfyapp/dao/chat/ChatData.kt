package fdz.migue.housfyapp.dao.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_message")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val userPhotoUrl: String? = null,
    val message: String
)