package fdz.migue.housfyapp.dao.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val status: String,
    val profileImageUri: String? = null
)