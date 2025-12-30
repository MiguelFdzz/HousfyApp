package fdz.migue.housfyapp.dao.shopping

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_carts")
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val content: String
)