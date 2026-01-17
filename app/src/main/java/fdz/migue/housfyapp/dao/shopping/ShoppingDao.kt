package fdz.migue.housfyapp.dao.shopping

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping_carts")
    fun getAllCarts(): Flow<List<ShoppingCart>>

    @Query("SELECT * FROM shopping_carts WHERE id = :id LIMIT 1")
    suspend fun getCartById(id: String): ShoppingCart?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: ShoppingCart)

    @Update
    suspend fun updateCart(cart: ShoppingCart)

    @Delete
    suspend fun deleteCart(cart: ShoppingCart)

    @Query("DELETE FROM shopping_carts WHERE id = :id")
    suspend fun deleteCartById(id: String)

    @Query("DELETE FROM shopping_carts")
    suspend fun deleteAll()
}