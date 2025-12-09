package fdz.migue.housfyapp.features.tasks

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val text: String
)