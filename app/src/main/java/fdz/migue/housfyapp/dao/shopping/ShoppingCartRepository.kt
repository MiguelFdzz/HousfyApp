package fdz.migue.housfyapp.dao.shopping

import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {

    fun getCarts(): Flow<List<ShoppingCart>>

    suspend fun getCartById(id: String): ShoppingCart?

    suspend fun saveCart(cart: ShoppingCart)

    suspend fun deleteCart(cart: ShoppingCart)

    suspend fun deleteCartById(id: String)
}

class ShoppingCartRepositoryImpl(private val dao: ShoppingDao) : ShoppingCartRepository {

    override fun getCarts(): Flow<List<ShoppingCart>> {
        return dao.getAllCarts()
    }

    override suspend fun getCartById(id: String): ShoppingCart? {
        return dao.getCartById(id)
    }

    override suspend fun saveCart(cart: ShoppingCart) {
        dao.insertCart(cart)
    }

    override suspend fun deleteCart(cart: ShoppingCart) {
        dao.deleteCart(cart)
    }

    override suspend fun deleteCartById(id: String) {
        dao.deleteCartById(id)
    }
}